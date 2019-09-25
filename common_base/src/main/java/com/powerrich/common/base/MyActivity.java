package com.powerrich.common.base;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.gyf.barlibrary.ImmersionBar;
import com.powerrich.common.widget.TitleBar;
//import com.hjq.bar.TitleBar;

import com.powerrich.common.helper.ActivityStackManager;
import com.powerrich.common.helper.DebugUtils;
import com.powerrich.common.other.EventBusManager;
import com.powerrich.common.other.StatusManager;
import com.hjq.toast.ToastUtils;
//import com.hjq.umeng.UmengClient;
//
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author : AlienChao
 * time   : 2019/7/25
 * desc : 标题栏的设置、Dialog的显示、日志的打印
 */

public abstract class MyActivity extends BaseActivity implements TitleBar.OnTitleBarListener {

    @Override
    protected void initActivity() {
        super.initActivity();
        ActivityStackManager.getInstance().onActivityCreated(this);
    }


    /**
     * ButterKnife 注解
     */
    private Unbinder mButterKnife;
    /**
     * 标题栏对象
     */
    private TitleBar mTitleBar;

    private ImmersionBar mImmersionBar;

    @Override
    protected void initLayout() {
        super.initLayout();

        // 初始化标题栏的监听
        if (getTitleId() > 0) {
            View viewById = findViewById(getTitleId());
            if (viewById instanceof TitleBar) {
                mTitleBar = (TitleBar) viewById;
            }
        } else if (getTitleId() == 0) {
            TitleBar titleBar = findTitleBar((ViewGroup) getContentView());
            if (titleBar != null) {
                mTitleBar = titleBar;
            }
        }

        // 初始化标题栏的监听与标题
        if (mTitleBar != null) {
            mTitleBar.setOnTitleBarListener(this);
            mTitleBar.setTitle(getTitle());
        }

        mButterKnife = ButterKnife.bind(this);
        EventBusManager.register(this);
        initOrientation();
        initImmersion();
    }


    /**
     * 初始化沉浸式
     */
    protected void initImmersion() {
        //初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            statusBarConfig().init();

            //设置标题栏 状态栏与布局顶部重叠解决方案 详情见：https://github.com/gyf-dev/ImmersionBar
            ////可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
            if (getTitleId() > 0) {
                ImmersionBar.setTitleBar(this, findViewById(getTitleId()));
            }else if(getTitleId()==0){
                if(mTitleBar!=null){
                    ImmersionBar.setTitleBar(this, mTitleBar);
                }
            }
        }
    }


    /**
     * 初始化沉浸式状态栏
     */
    protected ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont());   //默认状态栏字体颜色为黑色
//                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
//                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        return mImmersionBar;
    }

    /**
     * 是否使用沉浸式状态栏
     */
    public boolean isStatusBarEnabled() {
        return true;
    }



    private TitleBar findTitleBar(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View childAt = group.getChildAt(i);
            if (childAt instanceof TitleBar) {
                return (TitleBar) childAt;
            } else if (childAt instanceof ViewGroup) {
                TitleBar titleBar = findTitleBar((ViewGroup) childAt);
                if (titleBar != null) {
                    return titleBar;
                }
            }
        }
        return null;
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        // 当前 Activity 不能是透明的并且没有指定屏幕方向，默认设置为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
        }
    }

    @Nullable
    public TitleBar getTitleBar() {
        if (getTitleId() > 0 && findViewById(getTitleId()) instanceof TitleBar) {
            return findViewById(getTitleId());
        }
        return null;
    }


    public boolean statusBarDarkFont() {
        //返回true表示黑色字体 false 白色
        return true;
    }


    @Override
    public void onLeftClick() {
        onBackPressed();
    }


    public TitleBar getTilteBar() {
        if (getTitleId() > 0 && findViewById(getTitleId()) instanceof TitleBar) {

        }
        return null;
    }


    @Override
    public void setLeftTitle(String title) {

    }

    @Override
    public void onRightClick() {

    }


    //    /**
//     * {@link OnTitleBarListener}
//     */

//    // TitleBar 左边的View被点击了
//    @Override
//    public void onLeftClick(View v) {
//        onBackPressed();
//    }
//
//    // TitleBar 中间的View被点击了
//    @Override
//    public void onTitleClick(View v) {}
//
//    // TitleBar 右边的View被点击了
//    @Override
//    public void onRightClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        // UmengClient.onResume(this);
    }

    @Override
    protected void onPause() {
        // UmengClient.onPause(this);
        super.onPause();
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    public ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) {
            mButterKnife.unbind();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        EventBusManager.unregister(this);
        ActivityStackManager.getInstance().onActivityDestroyed(this);
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(@StringRes int id) {
        ToastUtils.show(getString(id));
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }

    /**
     * 打印日志
     */
    public void log(Object object) {
        if (DebugUtils.isDebug(this)) {
            Log.v(getClass().getSimpleName(), object != null ? object.toString() : "null");
        }
    }

    /**
     * 获取当前的 Application 对象
     */
    public final BaseApplication getMyApplication() {
        return (BaseApplication) getApplication();
    }

    private final StatusManager mStatusManager = new StatusManager();

    /**
     * 显示加载中
     */
    public void showLoading() {
        mStatusManager.showLoading(this);
    }

    /**
     * 显示加载完成
     */
    public void showComplete() {
        mStatusManager.showComplete();
    }

    /**
     * 显示空提示
     */
    public void showEmpty() {
        mStatusManager.showEmpty(getContentView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getContentView());
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(@DrawableRes int iconId, @StringRes int textId) {
        mStatusManager.showLayout(getContentView(), iconId, textId);
    }

    public void showLayout(Drawable drawable, CharSequence hint) {
        mStatusManager.showLayout(getContentView(), drawable, hint);
    }
}