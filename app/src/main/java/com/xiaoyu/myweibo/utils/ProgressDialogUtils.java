package com.xiaoyu.myweibo.utils;

import android.app.ProgressDialog;

import com.xiaoyu.myweibo.base.BaseApplication;

/**
 * 正在加载...
 * Created by xiaoy on 16/9/18.
 */
public class ProgressDialogUtils {

    private static ProgressDialog mDialog;

    public static void ShowProgressDialog() {

        mDialog = new ProgressDialog(AppManager.getAppManager().currentActivity());
        mDialog.setMessage("正在加载...");
        mDialog.setIndeterminate(true);
        mDialog.setCancelable(false);

        mDialog.show();
    }

    public static void hideProgressDialog() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }
}
