package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.UserInfo;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GetUserInfoService {
    @GET("show.json")
    Observable<UserInfo> getUserInfo(@Query("uid") long uid,
                                     @Query("access_token") String accessToken);
}
