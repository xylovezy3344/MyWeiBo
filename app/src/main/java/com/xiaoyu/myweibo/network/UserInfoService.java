package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.UserInfoBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface UserInfoService {
    @GET("show.json")
    Observable<UserInfoBean> getUserInfo(@Query("uid") long uid,
                                         @Query("access_token") String accessToken);
}
