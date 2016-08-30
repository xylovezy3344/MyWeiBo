package com.xiaoyu.myweibo.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoyu.myweibo.base.BaseApplication;

/**
 * 图片加载工具
 * Created by xiaoyu on 16-8-27.
 */
public class LoadImage {
    public static void loadImage(Object imageAddress, ImageView view) {
        Glide.with(BaseApplication.context())
                .load(imageAddress)
                .into(view);
    }
}
