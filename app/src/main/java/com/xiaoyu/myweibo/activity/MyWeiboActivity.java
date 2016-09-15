package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWeiboActivity extends AppCompatActivity {

    @BindView(R.id.tvttt)
    TextView mTvttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_weibo_act);
        ButterKnife.bind(this);

        String tag = getIntent().getStringExtra("tag");
        mTvttt.setText(tag);
    }
}
