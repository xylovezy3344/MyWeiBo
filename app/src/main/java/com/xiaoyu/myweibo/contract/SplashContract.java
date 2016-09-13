package com.xiaoyu.myweibo.contract;

import android.content.Intent;

/**
 * View和Presenter契约类
 */
public interface SplashContract {

    interface View  {
        void jumpToMain();
    }

    interface Presenter {

        boolean isLogin();

        void loginWeiBo();

        void authorizeCallBack(int requestCode, int resultCode, Intent data);

    }
}
