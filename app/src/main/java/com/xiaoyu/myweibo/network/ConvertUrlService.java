package com.xiaoyu.myweibo.network;

import com.xiaoyu.myweibo.bean.LongUrlBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 获取长链接
 * Created by xiaoyu on 16-9-11.
 */
public interface ConvertUrlService {

    @GET("expand.json")
    Observable<LongUrlBean> getLongUrl(@Query("access_token") String accessToken,
                                       @Query("url_short") String shortUrl);

}
