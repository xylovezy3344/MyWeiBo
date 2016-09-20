package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfoBean;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 调用openApi根据Uid获取用户信息
 * Created by xiaoyu on 16-9-14.
 */
public class UserInfo {

    public static void getUserInfo(long uid, Observer<UserInfoBean> observer) {

        String baseUrl = "https://api.weibo.com/2/users/";

        UserInfoService userInfoService = NetWorkUtils.getRetrofit(baseUrl)
                .create(UserInfoService.class);

        userInfoService.getUserInfo(uid, BaseApplication.accessToken().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
