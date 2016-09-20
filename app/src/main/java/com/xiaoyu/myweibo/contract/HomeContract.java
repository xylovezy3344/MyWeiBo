package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.base.BasePresenter;
import com.xiaoyu.myweibo.base.BaseView;
import com.xiaoyu.myweibo.bean.UserInfoBean;

/**
 * 微博主页面
 * Created by xiaoyu on 16-9-11.
 */
public interface HomeContract {

    interface View extends BaseView {
        void showUserInfo(UserInfoBean userInfoBean);
    }

    interface Presenter extends BasePresenter {
        void getUserInfo();
    }
}
