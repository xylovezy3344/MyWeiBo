package com.xiaoyu.myweibo.login;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.utils.LoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {

        LoadImage.loadImage("http://i.meizitu.net/2013/08/131Z5K41-0.jpg", mImage);

    }
}
