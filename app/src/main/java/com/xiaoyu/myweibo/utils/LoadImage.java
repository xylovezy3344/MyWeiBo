package com.xiaoyu.myweibo.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoyu.myweibo.base.BaseApplication;

import java.util.Stack;

/**
 * 图片加载工具
 * Created by xiaoyu on 16-8-27.
 */
public class LoadImage {

    private static LoadImage instance;

    private LoadImage() {
    }

    public static LoadImage getInstance() {
        if (instance == null) {
            instance = new LoadImage();
        }
        return instance;
    }

    public void loadImage(Object imageAddress, ImageView view) {
        Glide.with(BaseApplication.context())
                .load(imageAddress)
                .into(view);
    }
}
