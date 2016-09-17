package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.LongUrl;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 微博内容的短链接转长链接
 * Created by xiaoyu on 16-9-11.
 */
public class GetLongUrl {

    public static void getLongUrl(String shortUrl, Observer<LongUrl> observer) {

        String baseUrl = "https://api.weibo.com/2/short_url/";

        GetLongUrlService getLongUrlService = NetWorkUtils.getRetrofit(baseUrl)
                .create(GetLongUrlService.class);

        getLongUrlService.getLongUrl(BaseApplication.accessToken().getToken(), shortUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
