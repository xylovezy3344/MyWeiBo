package com.xiaoyu.myweibo.appstart;

import android.content.Intent;

/**
 * View和Presenter契约类
 */
public interface LoginContract {

    interface View  {
        void jumpToMain();
    }

    interface Presenter {

        boolean isLogin();

        void loginWeiBo();

        void authorizeCallBack(int requestCode, int resultCode, Intent data);
    }
}
