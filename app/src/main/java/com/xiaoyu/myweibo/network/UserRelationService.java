package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.RelationInfoList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 获取用户关系
 * Created by xiaoyu on 16-9-11.
 */
public interface UserRelationService {

    //获取关注列表
    @GET("friends.json")
    Observable<RelationInfoList> getFriendList(@Query("access_token") String accessToken,
                                               @Query("uid") long uid,
                                               @Query("count") int count,
                                               @Query("cursor") int cursor);

    //获取粉丝列表
    @GET("followers.json")
    Observable<RelationInfoList> getFollowerList(@Query("access_token") String accessToken,
                                                 @Query("uid") long uid,
                                                 @Query("count") int count,
                                                 @Query("cursor") int cursor);
}
