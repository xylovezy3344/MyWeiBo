package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;

/**
 * 用户主页页面
 */
public class UserHomeActivity extends BaseActivity {

    public static final String USER_UID = "USER_UID";
    public static final String USER_NAME = "USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_act);

        String userName = getIntent().getStringExtra(USER_NAME);
        long UserUid = getIntent().getLongExtra(USER_UID, -1);

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户主页页面--" + UserUid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "用户主页页面--" + userName, Toast.LENGTH_SHORT).show();
        }


    }
}
