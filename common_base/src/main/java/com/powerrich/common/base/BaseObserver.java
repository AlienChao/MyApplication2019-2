package com.powerrich.common.base;

import com.chen.concise.Response;
import com.chen.concise.RxExceptionUtil;
import com.hjq.toast.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author AlienChao
 * date 2019/08/05 10:27
 */
public abstract class BaseObserver<T> implements Observer<Response<T>> {


    @Override
    public void onNext(Response<T> tResponse) {
        if (tResponse.getERROR_CODE() == 0) {
            onSuccess(tResponse.getRESPONSE_DATA());
        } else {
            onError(tResponse.getERROR_MSG());
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(RxExceptionUtil.exceptionHandler(e));
        ToastUtils.show(RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public abstract void onSuccess(T t);


    protected void onError(String errorStr) {
        ToastUtils.show(errorStr);
    }
}
