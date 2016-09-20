package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.RelationInfoList;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取用户关系
 * Created by xiaoyu on 16-9-11.
 */
public class UserRelation {

    //每次向服务器获取数据条数
    private static final int ONCE_COUNT = 60;

    /**
     * 获取关注列表
     * @param cursor    游标
     * @param observer  观察者
     */
    public static void getFriendList(int cursor, Observer<RelationInfoList> observer) {

        String baseUrl = "https://api.weibo.com/2/friendships/";

        UserRelationService userRelationService = NetWorkUtils.getRetrofit(baseUrl)
                .create(UserRelationService.class);

        userRelationService.getFriendList(BaseApplication.accessToken().getToken(),
                Long.parseLong(BaseApplication.accessToken().getUid()), ONCE_COUNT,
                cursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取粉丝列表
     * @param cursor    游标
     * @param observer  观察者
     */
    public static void getFollowerList(int cursor, Observer<RelationInfoList> observer) {

        String baseUrl = "https://api.weibo.com/2/friendships/";

        UserRelationService userRelationService = NetWorkUtils.getRetrofit(baseUrl)
                .create(UserRelationService.class);

        userRelationService.getFollowerList(BaseApplication.accessToken().getToken(),
                Long.parseLong(BaseApplication.accessToken().getUid()), ONCE_COUNT,
                cursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
