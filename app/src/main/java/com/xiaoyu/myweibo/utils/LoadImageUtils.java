package com.xiaoyu.myweibo.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.xiaoyu.myweibo.base.BaseApplication;

/**
 * 图片加载工具
 * Created by xiaoyu on 16-8-27.
 */
public class LoadImageUtils {

    private static LoadImageUtils instance;

    private LoadImageUtils() {
    }

    public static LoadImageUtils getInstance() {
        if (instance == null) {
            instance = new LoadImageUtils();
        }
        return instance;
    }

    public void loadImageAsBitmap(Object imageAddress, ImageView view) {
        Glide.with(BaseApplication.context())
                .load(imageAddress)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(view);
    }

    public void loadImageAsGif(Object imageAddress, ImageView view) {
        Glide.with(BaseApplication.context())
                .load(imageAddress)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * 需要在子线程执行
     * @param url url
     * @return Bitmap
     */
    public Bitmap loadBitmap(String url) {
        try {
            return Glide.with(BaseApplication.context())
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
