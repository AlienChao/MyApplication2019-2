package com.powerrich.common.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.common.R;


import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * author : AlienChao
 * time   : 2019/7/25
 * desc :  支持沉浸式和侧滑的Activity基类（默认开启沉浸式状态栏和侧滑功能） 状态栏字体为黑色
 * detailed：是否使用沉浸式状态栏、是否使用滑动返回、
 */

public abstract class UIActivity extends MyActivity
        implements BGASwipeBackHelper.Delegate {



    private BGASwipeBackHelper mSwipeBackHelper;//侧滑返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBack();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initLayout() {
        super.initLayout();

    }






    public BGASwipeBackHelper getSwipeBackHelper() {
        return mSwipeBackHelper;
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBack() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * {@link BGASwipeBackHelper.Delegate}
     */

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     */
    @Override
    public boolean isSupportSwipeBack() {
        // android 9.0系统滑动返回上一级时闪屏：https://github.com/bingoogolapple/BGASwipeBackLayout-Android/issues/154
        return true; //Build.VERSION.SDK_INT < Build.VERSION_CODES.P
    }


    /**
     * 正在滑动返回
     *
     * param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            mSwipeBackHelper.swipeBackward();
//            //backwardAndFinish
//            mSwipeBackHelper.backwardAndFinish();
        }else{
            BGAKeyboardUtil.closeKeyboard(this);
            finish();
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }

        mSwipeBackHelper.backward();
        super.onBackPressed();
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}