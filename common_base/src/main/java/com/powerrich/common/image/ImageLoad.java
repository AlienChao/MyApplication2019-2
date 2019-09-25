package com.powerrich.common.image;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.hjq.common.R;

import java.security.MessageDigest;

/**
 * @author AlienChao
 * date 2019/07/31 16:42
 */
public class ImageLoad {


    public static void loadImage(@NonNull ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (null == imageView) {
            return;
        }

        Glide.with(imageView.getContext())
                .load(url.trim())
                .apply(RequestOptions.errorOf(R.mipmap.public_img_failed).placeholder(R.mipmap.public_img_loading))
                .into(imageView);
    }

    public static void loadImage(@NonNull ImageView imageView, int resourceId) {
        if (resourceId == 0) {
            return;
        }
        if (null == imageView) {
            return;
        }

        Glide.with(imageView.getContext())
                .load(resourceId)
                .apply(RequestOptions.errorOf(R.mipmap.public_img_failed).placeholder(R.mipmap.public_img_loading))
                .into(imageView);
    }


    public static void loadCircleImage(@NonNull ImageView imageView, String url) {
        if (url != null && !"".equals(url)) {
            Glide.with(imageView.getContext())
                    .load(url.trim())
                    .apply(RequestOptions.errorOf(R.mipmap.public_img_failed).placeholder(R.mipmap.public_img_loading).transform(new CircleTransformation()))
                    .into(imageView);
        }
    }


    private static final class CircleTransformation extends BitmapTransformation {

        private final String ID = getClass().getName();

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
            int x = (toTransform.getWidth() - size) / 2;
            int y = (toTransform.getHeight() - size) / 2;

            Bitmap square = Bitmap.createBitmap(toTransform, x, y, size, size);
            Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(circle);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return circle;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CircleTransformation) {
                return this == obj;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Util.hashCode(ID.hashCode());
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID.getBytes(CHARSET));
        }
    }

}
