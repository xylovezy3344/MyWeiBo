package com.xiaoyu.myweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.xiaoyu.myweibo.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    @BindView(R.id.fl_splash)
    FrameLayout mFlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
        ButterKnife.bind(this);

        mFlSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpNextActivity();
            }
        }, 1000);

    }

    private void jumpNextActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
