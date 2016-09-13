package com.xiaoyu.myweibo.utils;

import android.os.Environment;

import com.xiaoyu.myweibo.base.BaseApplication;

import java.io.File;
import java.util.ArrayList;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

/**
 * 查看图片工具类
 * Created by xiaoyu on 16-9-11.
 */
public class PreviewPhoto {

    public static void previewPhoto(String photoPath) {

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "MyWeiBoDownload");

        // 预览单张图片
        AppManager.getAppManager().currentActivity().startActivity(BGAPhotoPreviewActivity
                .newIntent(BaseApplication.context(), downloadDir, photoPath));
    }

    public static void previewPhotos(ArrayList<String> photos, int position) {

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "MyWeiBoDownload");

        // 预览多张图片
        AppManager.getAppManager().currentActivity().startActivity(BGAPhotoPreviewActivity
                .newIntent(BaseApplication.context(), downloadDir, photos, position));

    }
}
