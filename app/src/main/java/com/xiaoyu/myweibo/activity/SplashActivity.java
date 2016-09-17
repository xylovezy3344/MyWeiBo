package com.xiaoyu.myweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.contract.SplashContract;
import com.xiaoyu.myweibo.presenter.SplashPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首屏、闪屏页
 */
public class SplashActivity extends Activity implements SplashContract.View {

    @BindView(R.id.fl_splash)
    FrameLayout mFlSplash;

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
        ButterKnife.bind(this);

        mSplashPresenter = new SplashPresenter(this);

        mFlSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSplashPresenter.isLogin()) {
                    jumpToMain();
                } else {
                    mSplashPresenter.loginWeiBo();
                }
            }
        }, 500);

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
        mSplashPresenter.authorizeCallBack(requestCode, resultCode, data);
    }

    /**
     * 跳转主页面
     */
    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
