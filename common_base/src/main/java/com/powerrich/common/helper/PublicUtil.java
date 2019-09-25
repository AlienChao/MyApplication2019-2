package com.powerrich.common.helper;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;

/**
 * 常用工具类
 * @author AlienChao
 * date 2019/08/01 09:16
 */
public class PublicUtil {


    /**
     * 配置 RecyclerView
     *
     * param recyclerView
     * param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 改变Recycler的滑动速度
     *
     * param recyclerView
     * param velocity     //滑动速度默认是8000dp
     */
    public static void setMaxFlingVelocity(RecyclerView recyclerView, int velocity) {
        try {
            Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
            field.setAccessible(true);
            field.set(recyclerView, velocity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * dp 转 px
     *
     * param context {@link Context}
     * param dpValue {@code dpValue}
     * return {@code pxValue}
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * param context {@link Context}
     * param pxValue {@code pxValue}
     * return {@code dpValue}
     */
    public static int pix2dip(@NonNull Context context, int pxValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * param context {@link Context}
     * param spValue {@code spValue}
     * return {@code pxValue}
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * param context {@link Context}
     * param pxValue {@code pxValue}
     * return {@code spValue}
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

}
