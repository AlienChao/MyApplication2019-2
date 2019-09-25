package com.powerrich.common.base;

import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;

//import com.hjq.demo.com.example.fcoa.EventBusManager;
//import com.hjq.image.ImageLoader;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.hjq.common.R;
import com.hjq.toast.ToastUtils;


import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.powerrich.common.other.EventBusManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 项目中的 Application 基类
 */
public class BaseApplication extends Application {

    protected static Gson mGson;

    public static Gson getmGson() {
        return mGson;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (mGson == null) {
            mGson = new Gson();
        }
        initSDK(this);
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSDK(Application application) {
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(application, null);

        // 初始化路由
        ARouter.init(application);

        // 初始化吐司工具类
        ToastUtils.init(application);

        // 初始化图片加载器
     //   ImageLoader.init(application);

        // 初始化 EventBus
        EventBusManager.init();

       CrashHandler.getInstance().setCustomCrashHanler(application);


//        // 初始化友盟 SDK
//        UmengClient.init(application);


        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
               // layout.setPrimaryColorsId(R.color.public_white);//全局设置主题颜色 colorPrimary
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);
    }
}