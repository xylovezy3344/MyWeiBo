package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.base.BasePresenter;
import com.xiaoyu.myweibo.base.BaseView;
import com.xiaoyu.myweibo.bean.UserInfo;

/**
 * 我的主页
 * Created by xiaoyu on 16-9-11.
 */
public interface MyselfContract {

    interface View extends BaseView {
        void showUserInfo(UserInfo userInfo);
    }

    interface Presenter extends BasePresenter {
        void getUserInfo();
    }
}
