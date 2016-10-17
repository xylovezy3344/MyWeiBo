package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.CommentList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.sina.weibo.sdk.openapi.legacy.CommonAPI.CAPITAL.o;

public interface CommentsService {
    @GET("show.json")
    Observable<CommentList> getComments(@Query("id") long id,
                                        @Query("count") int count,
                                        @Query("access_token") String accessToken,
                                        @Query("since_id") long sinceId,
                                        @Query("max_id") long maxId);
}
