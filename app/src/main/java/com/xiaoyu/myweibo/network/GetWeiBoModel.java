package com.xiaoyu.myweibo.network;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiboDetailList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 调用openApi网络获取微博
 * Created by xiaoyu on 16-8-29.
 */
public class GetWeiboModel {

    //每次获取微博数量
    private static final int GET_WEIBO_NUM = 20;

    private static void getWeiBo(long sinceId, long maxId, Observer<WeiboDetailList> observer) {

        String baseUrl = "https://api.weibo.com/2/statuses/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GetWeiboService getWeiboService = retrofit.create(GetWeiboService.class);

        Logger.d(BaseApplication.accessToken().getToken());

        getWeiboService.getWeiBoDetail(GET_WEIBO_NUM, BaseApplication.accessToken().getToken(), sinceId, maxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取最新的GET_WEIBO_NUM条微博
     */
    public static void getLatestWeiBo(Observer<WeiboDetailList> observer) {
        getWeiBo(0, 0, observer);
    }

    /**
     * 获取比sinceId微博新的微博
     */
    public static void getNewWeiBo(long sinceId, Observer<WeiboDetailList> observer) {
        getWeiBo(sinceId, 0, observer);
    }

    /**
     * 获取maxId之前的微博
     */
    public static void getOldWeiBo(long maxId, Observer<WeiboDetailList> observer) {
        getWeiBo(0, maxId, observer);
    }
}
