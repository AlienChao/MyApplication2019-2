package com.powerrich.common.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.powerrich.common.helper.DebugUtils;
import com.powerrich.common.other.EventBusManager;
import com.powerrich.common.other.StatusManager;

import com.hjq.toast.ToastUtils;
import com.powerrich.common.widget.TitleBar;


import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author : AlienChao
 * time   : 2019/7/26
 * desc :  项目中 Fragment 懒加载基类.  Toast、Log、Dialog
 */

public abstract class MyLazyFragment extends UILazyFragment  implements TitleBar.OnTitleBarListener{

    private Unbinder mButterKnife; // View注解

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initFragment() {

        // 初始化标题栏的监听
        if (getTitleId() > 0) {
            if (findViewById(getTitleId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleId())).setOnTitleBarListener(this);
            }
        }


        super.initFragment();
        EventBusManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) {
            mButterKnife.unbind();
        }
        EventBusManager.unregister(this);
    }

    @Nullable
    public TitleBar getTitleBar() {
        if (getTitleId() > 0 && findViewById(getTitleId()) instanceof TitleBar) {
            return findViewById(getTitleId());
        }
        return null;
    }


    @Override
    public void onLeftClick() {
        Log.e("jsc", "MyLazyFragment-onLeftClick:");
        getActivity().finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void setLeftTitle(String title) {

    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(int id) {
        ToastUtils.show(id);
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }

    /**
     * 打印日志
     */
    public void log(Object object) {
        if (DebugUtils.isDebug(getActivity())) {
            Log.v(getClass().getSimpleName(), object != null ? object.toString() : "null");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //UmengClient.onResume(this);
    }

    @Override
    public void onPause() {
      //  UmengClient.onPause(this);
        super.onPause();
    }

    private final StatusManager mStatusManager = new StatusManager();

    /**
     * 显示加载中
     */
    public void showLoading() {
        mStatusManager.showLoading(getActivity());
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
        mStatusManager.showEmpty(getView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getView());
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(@DrawableRes int iconId, @StringRes int textId) {
        mStatusManager.showLayout(getView(), iconId, textId);
    }

    public void showLayout(Drawable drawable, CharSequence hint) {
        mStatusManager.showLayout(getView(), drawable, hint);
    }
}