package com.powerrich.common.base;

import android.graphics.Bitmap;

import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @author chenhao
 * date 2018/7/17
 * RecyclerView通用的ViewHolder
 */
public class CommonViewHolderRv extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView;

    //构造函数
    private CommonViewHolderRv(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
     //   AutoUtils.auto(mConvertView);
    }

    //获取一个ViewHolder
    public static CommonViewHolderRv getHolder(View view) {
        CommonViewHolderRv holder = new CommonViewHolderRv(view);
        return holder;
    }

    //通过控件的id获取对应的控件，如果没有则加入mViews;记住 <T extends View> T 这种用法
    public <T extends View> T getItemView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }



    public <T extends EditText> T getEditTextView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        EditText editText = (T) view;
       // editText.addTextChangedListener();
        return (T) view;
    }


    /**
     * 为TextView赋值
     */
    public void setText(int viewId, String text) {
        TextView view = getItemView(viewId);
        view.setText(text);
    }

    /**
     * 为ImageView赋值——drawableId
     */
    public void setImageResource(int viewId, int drawableId) {
        ImageView view = getItemView(viewId);
        view.setImageResource(drawableId);
    }

    /**
     * 为ImageView赋值——bitmap
     */
    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getItemView(viewId);
        view.setImageBitmap(bitmap);
    }

}
