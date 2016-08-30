package com.xiaoyu.myweibo.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.xiaoyu.myweibo.utils.AccessTokenKeeper;

public class BaseApplication extends Application {

    static Context _context;
    static Resources _resource;
    static Oauth2AccessToken _accessToken;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        _accessToken = AccessTokenKeeper.readAccessToken(_context);
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    public static Oauth2AccessToken accessToken() {
        return _accessToken;
    }

    public static void setAccessToken(Oauth2AccessToken accessToken) {
        _accessToken = accessToken;
    }
}
