package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.adapter.FriendsListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的关注、我的粉丝
 */

public class MyFriendActivity extends AppCompatActivity {

    @BindView(R.id.rv_user_list)
    RecyclerView mRvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_friend_act);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

        String tag = getIntent().getStringExtra("tag");
        mRvUserList.setLayoutManager(new LinearLayoutManager(this));
        mRvUserList.setAdapter(new FriendsListAdapter());

    }
}
