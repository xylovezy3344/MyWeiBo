package com.xiaoyu.myweibo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiaoyu.myweibo.AppManager;

import butterknife.ButterKnife;

/**
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
