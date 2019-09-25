package com.powerrich.common.base;



import com.chen.concise.Response;
import com.chen.concise.RxExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 通用类型
 */

public abstract class BaseStringObserver<T> implements Observer<T> {


	@Override
	public void onNext(T t) {
		onSuccess(t);
	}

	@Override
	public void onError(@NonNull Throwable e) {
		onFailure(e, RxExceptionUtil.exceptionHandler(e));
	}



	@Override
	public void onComplete() {

	}

	@Override
	public void onSubscribe(@NonNull Disposable d) {

	}

	public abstract void onSuccess(T result);

	public abstract void onFailure(Throwable e, String errorMsg);
}
