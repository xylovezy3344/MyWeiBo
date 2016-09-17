package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.WeiboDetailList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GetWeiboService {
    @GET("friends_timeline.json")
    Observable<WeiboDetailList> getWeiBoDetail(@Query("count") int count,
                                               @Query("access_token") String accessToken,
                                               @Query("since_id") long sinceId,
                                               @Query("max_id") long maxId);
}
