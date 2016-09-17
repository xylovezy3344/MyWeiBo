package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的微博、我的赞页面
 */
public class MyWeiboActivity extends BaseActivity {

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
