package com.xiaoyu.myweibo.appstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.xiaoyu.myweibo.R;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    public LoginPresenter(LoginContract.View loginView) {
        this.mLoginView = loginView;
    }

    @Override
    public void loginWeiBo() {

        //微博授权
        mAuthInfo = new AuthInfo((Activity) mLoginView, WeiBoLoginConstants.APP_KEY,
                WeiBoLoginConstants.REDIRECT_URL, null);
        mSsoHandler = new SsoHandler((Activity) mLoginView, mAuthInfo);

        mSsoHandler.authorize(new AuthListener());

    }

    @Override
    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            /*//从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken((Activity) mLoginView, mAccessToken);
                Toast.makeText((Activity) mLoginView,
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = ((Activity) mLoginView)
                        .getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText((Activity) mLoginView, message, Toast.LENGTH_LONG).show();
            }*/

            Logger.e(mAccessToken.getExpiresTime() / 1000 / 3600 / 24 / 30 + "");

        }

        @Override
        public void onCancel() {
            Toast.makeText((Activity) mLoginView, ((Activity) mLoginView)
                    .getString(R.string.weibosdk_demo_toast_auth_canceled),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText((Activity) mLoginView,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
