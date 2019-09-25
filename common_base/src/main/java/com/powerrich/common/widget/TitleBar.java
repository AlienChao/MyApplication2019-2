package com.powerrich.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hjq.common.R;
import com.powerrich.common.helper.PublicUtil;

/**
 * @author AlienChao
 */
public class TitleBar extends LinearLayout {
    private Context mContext;
    private View mRootView = null;
    private OnTitleBarListener mOnTitleBarListener;
    private TextView mTvTitle;
    private ImageView ivTitleLeft;
    private TextView tvTitleLeft;
    private TextView publicToolbarTitle;
    private TextView publicToolbarRight;
    private ImageView publicIvRight;


    public void setOnTitleBarListener(OnTitleBarListener onTitleBarListener) {
        mOnTitleBarListener = onTitleBarListener;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.public_include_title, this);
        initView();
//        ImageView ivTitleLeft = mRootView.findViewById(R.id.iv_title_left);
//        mTvTitle = mRootView.findViewById(R.id.public_toolbar_title);
        ivTitleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jsc", "TitleBar-onClick:");
                if (mOnTitleBarListener != null) {
                    mOnTitleBarListener.onLeftClick();
                }
            }
        });
        publicIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jsc", "TitleBar-onClick:");
                if (mOnTitleBarListener != null) {
                    mOnTitleBarListener.onRightClick();
                }
            }
        });
        getAttributes(context, attrs, defStyleAttr);
    }


    public void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PublicTitleBar);
        //是否开启默认样式
        String title = array.getString(R.styleable.PublicTitleBar_publicTitle);
        setTitle(title);
    }



    public void setTitle(CharSequence title) {
        mTvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
    }

    private void initView() {
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_left);
        mTvTitle = (TextView) findViewById(R.id.public_toolbar_title);
        publicToolbarTitle = (TextView) findViewById(R.id.public_toolbar_title);
        publicToolbarRight = (TextView) findViewById(R.id.public_toolbar_right);
        publicIvRight = (ImageView) findViewById(R.id.public_iv_right);
    }



    public TextView getTvTitleLeft() {
        return tvTitleLeft;
    }


    public void setLeftTitle(String title) {
        ivTitleLeft.setVisibility(View.GONE);
        tvTitleLeft.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvTitleLeft.getLayoutParams();
        layoutParams.leftMargin = PublicUtil.dip2px(mContext, 10);
        tvTitleLeft.setLayoutParams(layoutParams);
        tvTitleLeft.setText(title);
    }


    public void setIvRight(int resouceId) {
        publicIvRight.setImageResource(resouceId);
    }


    public interface OnTitleBarListener {
        /**
         * 左边点击
         */
        void onLeftClick();


        /**
         * 右边点击
         */
        void onRightClick();




        /**
         * 设置左边的标题
         *
         * param title
         */
        default void setLeftTitle(String title) {
        }

        ;

    }

}
