package com.powerrich.common.other;

import androidx.fragment.app.FragmentActivity;

import com.powerrich.common.base.BaseObserver;
import com.powerrich.common.base.BaseDialog;
import com.powerrich.common.dialog.WaitDialog;

import io.reactivex.disposables.Disposable;

/**
 * @author AlienChao
 */
public abstract class ProgressObserver<T> extends BaseObserver<T> {

    private BaseDialog mBaseDialog;

    private FragmentActivity mActivity;
    private String mloadingStr;
    public ProgressObserver( FragmentActivity mActivity) {
        this.mActivity =mActivity;
    }
    public ProgressObserver( FragmentActivity mActivity,String loadingText) {
        this.mActivity =mActivity;
        this.mloadingStr = loadingText;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if(!d.isDisposed()){
            mBaseDialog = new WaitDialog.Builder(mActivity).show();
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if(mBaseDialog!=null){
            mBaseDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(mBaseDialog!=null){
            mBaseDialog.dismiss();
        }
    }
}
