package com.xiaoyu.myweibo.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;

/**
 * 用户主页页面
 */
public class UserHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_act);

        String userName = getIntent().getStringExtra("user_name");

        Toast.makeText(UserHomeActivity.this, "用户主页页面--" + userName, Toast.LENGTH_SHORT).show();
    }
}
