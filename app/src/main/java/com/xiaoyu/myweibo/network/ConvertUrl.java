package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.LongUrlBean;
import com.xiaoyu.myweibo.utils.NetWorkUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 微博内容的短链接转长链接
 * Created by xiaoyu on 16-9-11.
 */
public class ConvertUrl {

    public static void getLongUrl(String shortUrl, Observer<LongUrlBean> observer) {

        String baseUrl = "https://api.weibo.com/2/short_url/";

        ConvertUrlService convertUrlService = NetWorkUtils.getRetrofit(baseUrl)
                .create(ConvertUrlService.class);

        convertUrlService.getLongUrl(BaseApplication.accessToken().getToken(), shortUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
