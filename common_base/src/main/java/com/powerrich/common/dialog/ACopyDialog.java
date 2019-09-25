package com.powerrich.common.dialog;


import android.view.Gravity;

import androidx.fragment.app.FragmentActivity;

import com.hjq.common.R;
import com.powerrich.common.base.BaseDialog;
import com.powerrich.common.base.BaseDialogFragment;

/**
 *    desc   : 可进行拷贝的副本
 */
public final class ACopyDialog {

    public static final class Builder
            extends BaseDialogFragment.Builder<Builder> {

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.public_item_copy);
           setAnimStyle(BaseDialog.AnimStyle.SCALE);

            //setGravity(Gravity.BOTTOM);
            setGravity(Gravity.CENTER);

        }
    }
}