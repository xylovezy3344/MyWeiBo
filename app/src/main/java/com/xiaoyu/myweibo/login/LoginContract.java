package com.xiaoyu.myweibo.login;

import com.xiaoyu.myweibo.base.BasePresenter;
import com.xiaoyu.myweibo.base.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {


    }
}
