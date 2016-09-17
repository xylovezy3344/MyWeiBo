package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.adapter.RelationAdapter;
import com.xiaoyu.myweibo.base.BaseActivity;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.RelationInfoList;
import com.xiaoyu.myweibo.contract.MyRelationContract;
import com.xiaoyu.myweibo.fragment.MyselfFragment;
import com.xiaoyu.myweibo.presenter.MyRelationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的关注、我的粉丝页面
 */
public class MyRelationActivity extends BaseActivity implements MyRelationContract.View {

    @BindView(R.id.rv_user_list)
    RecyclerView mRvUserList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private ActionBar mActionBar;
    private MyRelationPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;

    //表示首次请求数据
    public static int FIRST_GET = 0;
    //上拉加载更多
    public static int UP_REFRESH = 1;

    private String mTag;
    private List<RelationInfoList.UsersBean> mUserList;
    private RelationAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_relation_act);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {

        mActionBar = getSupportActionBar();

        mPresenter = new MyRelationPresenter(this);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(this);
        mRvUserList.setLayoutManager(mLayoutManager);
        //设置Item增加、移除动画
        mRvUserList.setItemAnimator(new DefaultItemAnimator());

        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(false);
            }
        });

        //上拉加载更多
        mRvUserList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
                    if (!mRefreshLayout.isRefreshing()) {
                        mPresenter.getRelationList(UP_REFRESH, mUserList, mTag);
                        mRefreshLayout.setRefreshing(true);
                    }
                }
            }
        });
    }

    private void initData() {

        mTag = getIntent().getStringExtra("tag");

        if (mTag.equals(MyselfFragment.MY_FRIENDS)) {
            mActionBar.setTitle("我的关注");
        } else {
            mActionBar.setTitle("我的粉丝");
        }

        //首次请求
        mPresenter.getRelationList(FIRST_GET, null, mTag);
    }

    @Override
    public void showRelationList(List<RelationInfoList.UsersBean> list) {
        mUserList = list;
        //设置adapter
        mAdapter = new RelationAdapter(mUserList);
        mRvUserList.setAdapter(mAdapter);
    }

    @Override
    public void loadMore(List<RelationInfoList.UsersBean> list) {
        mUserList = list;
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }
}
