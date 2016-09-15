package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.bean.UserInfo;

/**
 * 我的主页
 * Created by xiaoyu on 16-9-11.
 */
public interface MyselfContract {

    interface View {
        void showUserInfo(UserInfo userInfo);
    }

    interface Presenter {
        void getUserInfo();
    }
}
