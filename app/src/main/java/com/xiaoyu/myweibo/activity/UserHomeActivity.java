package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;

/**
 * 用户主页页面
 */
public class UserHomeActivity extends BaseActivity {

    public static final String USER_UID = "USER_UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_act);

        getIntent().getLongExtra(USER_UID, 0);

    }
}
