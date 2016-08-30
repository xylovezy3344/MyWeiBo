package com.xiaoyu.myweibo.home;

import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GetWeiBoService {
    @GET("friends_timeline.json")
    Observable<WeiBoDetailList> getWeiBoDetail(@Query("count") int count,
                                               @Query("access_token") String accessToken,
                                               @Query("since_id") long sinceId,
                                               @Query("max_id") long maxId);
}
