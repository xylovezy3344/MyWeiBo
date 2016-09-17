package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfo;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 调用openApi根据Uid获取用户信息
 * Created by xiaoyu on 16-9-14.
 */
public class GetUserInfo {

    public static void getUserInfo(long uid, Observer<UserInfo> observer) {

        String baseUrl = "https://api.weibo.com/2/users/";

        GetUserInfoService getUserInfoService = NetWorkUtils.getRetrofit(baseUrl)
                .create(GetUserInfoService.class);

        getUserInfoService.getUserInfo(uid, BaseApplication.accessToken().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
