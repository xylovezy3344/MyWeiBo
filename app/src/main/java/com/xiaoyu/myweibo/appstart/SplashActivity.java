package com.xiaoyu.myweibo.appstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.xiaoyu.myweibo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity implements LoginContract.View {

    @BindView(R.id.fl_splash)
    FrameLayout mFlSplash;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this);

        mFlSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginPresenter.loginWeiBo();
            }
        }, 1000);

    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        mLoginPresenter.authorizeCallBack(requestCode, resultCode, data);
    }
}
