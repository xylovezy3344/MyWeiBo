package com.xiaoyu.myweibo.appstart;

import android.content.Intent;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoginContract {

    interface View  {
    }

    interface Presenter {

        void loginWeiBo();

        void authorizeCallBack(int requestCode, int resultCode, Intent data);
    }
}
