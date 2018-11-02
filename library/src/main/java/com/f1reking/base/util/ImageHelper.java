package com.f1reking.base.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author F1ReKing
 * @date 2018/11/2 14:04
 * @Description
 */
public class ImageHelper {

    public ImageHelper() {
        throw new Error("Do not need instantiate!");
    }

    public static void load(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void loadHolder(Context context, ImageView imageView, Object url,
        Object holderRes) {
        if (holderRes instanceof Drawable) {
            Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder((Drawable) holderRes))
                .into(imageView);
        } else {
            Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder((Integer) holderRes))
                .into(imageView);
        }
    }

    public static void loadError(Context context, ImageView imageView, Object url,
        Object errorRes) {
        if (errorRes instanceof Drawable) {
            Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error((Drawable) errorRes))
                .into(imageView);
        } else {
            Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error((Integer) errorRes))
                .into(imageView);
        }
    }

    public static void loadCircleCrop(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url).apply(new RequestOptions().circleCrop()).into(imageView);
    }

    public static void loadThumbnail(Context context, ImageView imageView, Object url) {
        Glide.with(context).load(url).thumbnail(0.1f).into(imageView);
    }

    public static void loadNone(Context context, ImageView imageView, Object url) {
        Glide.with(context)
            .load(url)
            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
            .into(imageView);
    }
}
