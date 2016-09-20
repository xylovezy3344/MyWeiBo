package com.xiaoyu.myweibo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaoyu.myweibo.R;

public class SingleWeiboActivity extends AppCompatActivity {

    public static final String SINGLE_WEIBO = "SINGLE_WEIBO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_weibo_act);

        String weiboUrl = getIntent().getStringExtra(SINGLE_WEIBO);
    }
}
