package com.powerrich.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.powerrich.common.other.RxSchedulers;
import com.powerrich.common.dialog.WaitDialog;
import com.powerrich.common.helper.LogUtils;

import java.lang.reflect.Field;
import java.util.Random;

import io.reactivex.Observable;

/**
 * author : AlienChao
 * time   : 2019/7/26
 * desc :  Fragment 懒加载基类。 简化/findViewById/startActivityFinish/startActivity/startactivityResult/getActivit
 * 默认不拦截按键事件，传递给Activity
 */

public abstract class BaseLazyFragment extends Fragment {

    private static final String TAG = "Fragment基类";

    // Activity对象
    public BaseActivity mActivity;
    // 根布局
    private View mRootView;
    // 是否进行过懒加载
    private boolean isLazyLoad;
    // Fragment 是否可见
    private boolean isFragmentVisible;
    // 是否是 replace Fragment 的形式  来回切换的时候会调用
    private boolean isReplaceFragment;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());



    /**
     * 执行http请求
     */
    public <T> Observable<T> exeHttp(Observable<T> observable) {
        return observable.compose(RxSchedulers.observableIO2Main());
    }

    /**
     * 带有dialog的请求
     */
    public <T> Observable<T> exeHttpWithDialog(Observable<T> observable) {
        BaseDialog dialog = new WaitDialog.Builder(getActivity()).show();
        return  exeHttp(observable).compose(RxSchedulers.<T>loadingDialog(dialog));
    }



    /**
     * 直接执行
     */
    public final boolean postPerform(Runnable r) {

        return postAtTime(r, SystemClock.uptimeMillis());
    }


    /**
     * 延迟一段时间执行
     */
    public final boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除和这个 Activity 相关的消息回调
        HANDLER.removeCallbacksAndMessages(this);
    }



    /**
     * 获得全局的，防止使用getActivity()为空
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) requireActivity();
    }

    /**
     * 获取绑定的Activity，防止出现 getActivity() 为空
     */
//    public A getBindingActivity() {
//        return (A)mActivity;
//    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, "BaseLazyFragment-onCreateView:"+this.getClass().getSimpleName());

        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 是否进行了懒加载
     */
    protected boolean isLazyLoad() {
        return isLazyLoad;
    }

    /**
     * 当前 Fragment 是否可见
     */
    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        LogUtils.i(TAG,"BaseLazyFragment-onActivityCreated:"+this.getClass().getSimpleName()+"-是否是replace Fragment的形式:"+isReplaceFragment+" -Fragment 是否可见:"+isFragmentVisible);
        super.onActivityCreated(savedInstanceState);
        if (isReplaceFragment) {
            if (isFragmentVisible) {
                initLazyLoad();
            }
        } else {
            initLazyLoad();
        }
    }

    // replace Fragment时使用，ViewPager 切换时会回调此方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.i(TAG, "BaseLazyFragment-setUserVisibleHint:"+this.getClass().getSimpleName()+"-Fragment 是否可见:"+isVisibleToUser+"-是否进行过懒加载："+isLazyLoad);

        super.setUserVisibleHint(isVisibleToUser);
        this.isReplaceFragment = true;
        this.isFragmentVisible = isVisibleToUser;
        if (isVisibleToUser && mRootView != null) {
            if (!isLazyLoad) {
                initLazyLoad();
            } else {
                // 从不可见到可见
                onRestart();
            }
        }
    }

    /**
     * 初始化懒加载
     */
    protected void initLazyLoad() {
        if (!isLazyLoad) {
            initFragment();
            isLazyLoad = true;
        }
    }

    /**
     * 从可见的状态变成不可见状态，再从不可见状态变成可见状态时回调
     */
    protected void onRestart() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //这似乎是新添加的嵌套片段支持中的一个错误。基本上，当孩子FragmentManager从活动中分离出来时，孩子的内部状态就会破裂。
        // 为我解决这个问题的一个短期解决方法是将以下内容添加到您呼叫onDetach()的每一个Fragment中getChildFragmentManager() 来自：https://cloud.tencent.com/developer/ask/51322
        // 解决java.lang.IllegalStateException: Activity has been destroyed 的错误
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initFragment() {
        initView();
        initData();
    }

    //引入布局
    protected abstract int getLayoutId();

    //标题栏id
    protected abstract int getTitleId();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    /**
     * 根据资源 id 获取一个 View 对象
     */
    protected <V extends View> V findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    protected <V extends View> V findActivityViewById(@IdRes int id) {
        return mActivity.findViewById(id);
    }


    /**
     * startActivity 方法优化
     */

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(mActivity, cls));
    }

    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivityFinish(new Intent(mActivity, cls));
    }

    public void startActivityFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult 方法优化
     */

    private BaseActivity.ActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    public void startActivityForResult(Class<? extends Activity> cls, BaseActivity.ActivityCallback callback) {
        startActivityForResult(new Intent(mActivity, cls), null, callback);
    }

    public void startActivityForResult(Intent intent, BaseActivity.ActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, BaseActivity.ActivityCallback callback) {
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        } else {
            // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
            throw new IllegalArgumentException("Error, The callback is not over yet");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        mActivity.finish();
        mActivity = null;
    }

    /**
     * 获取系统服务
     */
    @SuppressWarnings("unchecked")
    public <S extends Object> S getSystemService(@NonNull String name) {
        return (S) mActivity.getSystemService(name);
    }

    /**
     * Fragment返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //默认不拦截按键事件，传递给Activity
        return false;
    }
}