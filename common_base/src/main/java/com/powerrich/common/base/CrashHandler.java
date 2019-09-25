package com.powerrich.common.base;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.powerrich.common.helper.ActivityStackManager;
import com.powerrich.common.helper.AppUtil;

/**
 * @author AlienChao
 * date 2019/08/13 11:03
 */
public class CrashHandler  implements Thread.UncaughtExceptionHandler {

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;
    private CrashHandler() {}
    public static CrashHandler getInstance() {
        return instance;
    }
    public void setCustomCrashHanler(Context context) {
        mContext = context;
        //崩溃时将catch住异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();

        //使用Toast进行提示
       showToast(mContext,"很抱歉，程序异常即将退出！");
       //ToastUtils.show("很抱歉，程序异常即将退出！");
        //延时退出
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //关闭APP
        ActivityStackManager.getInstance().finishAllActivities();
        System.exit(0);
    }

    //线程中展示Toast
    private void showToast(final Context context, final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

}
