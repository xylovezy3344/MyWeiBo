package com.xiaoyu.myweibo.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;

/**
 * 微博话题页面
 */
public class TopicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_act);

        String topic = getIntent().getStringExtra("topic");

        Toast.makeText(TopicActivity.this, "微博话题页面--" + topic, Toast.LENGTH_SHORT).show();
    }
}
