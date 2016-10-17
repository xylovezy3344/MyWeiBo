package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.CommentList;
import com.xiaoyu.myweibo.bean.WeiboDetailList;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.id;

/**
 * 调用openApi网络获取微博
 * Created by xiaoyu on 16-8-29.
 */
public class Comments {

    //每次获取评论数量
    private static final int GET_COMMENT_NUM = 20;

    private static void getWeiBo(long id, long sinceId, long maxId,
                                 Observer<CommentList> observer) {

        String baseUrl = "https://api.weibo.com/2/statuses/";

        CommentsService commentsService = NetWorkUtils.getRetrofit(baseUrl)
                .create(CommentsService.class);

        commentsService.getComments(id, GET_COMMENT_NUM, BaseApplication.accessToken().getToken(),
                sinceId, maxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取最新的GET_COMMENT_NUM条评论
     */
    public static void getLatestComments(long id, Observer<CommentList> observer) {
        getWeiBo(id, 0, 0, observer);
    }

    /**
     * 获取比sinceId评论新的评论
     */
    public static void getNewComments(long id, long sinceId, Observer<CommentList> observer) {
        getWeiBo(id, sinceId, 0, observer);
    }

    /**
     * 获取maxId之前的评论
     */
    public static void getOldComments(long id, long maxId, Observer<CommentList> observer) {
        getWeiBo(id, 0, maxId, observer);
    }
}
