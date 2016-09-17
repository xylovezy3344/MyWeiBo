package com.xiaoyu.myweibo.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit包装类
 * Created by xiaoy on 16/9/17.
 */
public class NetWorkUtils {

    public static Retrofit getRetrofit(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }
}
