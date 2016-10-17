package com.xiaoyu.myweibo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.adapter.WeiboListAdapter;
import com.xiaoyu.myweibo.bean.WeiboDetailList;
import com.xiaoyu.myweibo.contract.WeiboContract;
import com.xiaoyu.myweibo.presenter.WeiboPresenter;
import com.xiaoyu.myweibo.utils.ProgressDialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeiboFragment extends Fragment implements WeiboContract.View {

    @BindView(R.id.rv_weibo_detail)
    RecyclerView mRvWeiboDetail;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private WeiboListAdapter mAdapter;
    private WeiboPresenter mWeiboPresenter;
    private List<WeiboDetailList.StatusesBean> mWeiBoDetailList;

    //表示首次请求数据
    public static int FIRST_GET = 0;
    //上拉加载更多
    public static int UP_REFRESH = 1;
    //下拉刷新
    public static int DOWN_REFRESH = 2;

    private int mScreenWidth;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadMore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_weibo_frag, container, false);
        ButterKnife.bind(this, view);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvWeiboDetail.setLayoutManager(mLayoutManager);
        //设置Item增加、移除动画
        mRvWeiboDetail.setItemAnimator(new DefaultItemAnimator());

        mWeiboPresenter = new WeiboPresenter(this);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mWeiboPresenter.getWeiBo(DOWN_REFRESH, mWeiBoDetailList);
            }
        });

        //上拉加载更多
        mRvWeiboDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
                    if (!mRefreshLayout.isRefreshing()) {
                        mRefreshLayout.setRefreshing(true);
                        isLoadMore = true;
                        mWeiboPresenter.getWeiBo(UP_REFRESH, mWeiBoDetailList);
                    }
                }
            }
        });

        //首次请求微博数据
        mWeiboPresenter.getWeiBo(FIRST_GET, null);

        return view;
    }

    @Override
    public void showWeiBo(List<WeiboDetailList.StatusesBean> list) {
        mWeiBoDetailList = list;
        //设置adapter
        mAdapter = new WeiboListAdapter(mWeiBoDetailList);
        mRvWeiboDetail.setAdapter(mAdapter);
    }

    @Override
    public void refreshWeiBo(List<WeiboDetailList.StatusesBean> list) {
        mWeiBoDetailList = list;
        mAdapter.notifyDataSetChanged();
        if (!isLoadMore) {
            mLayoutManager.scrollToPosition(0);
        }
        mRefreshLayout.setRefreshing(false);
    }

    /**
     * 供HomeActivity调用，菜单刷新按钮实现
     */
    public void refreshForActivity() {
        isLoadMore = false;
        mRefreshLayout.setRefreshing(true);
        mWeiboPresenter.getWeiBo(DOWN_REFRESH, mWeiBoDetailList);
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogUtils.ShowProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        ProgressDialogUtils.hideProgressDialog();
    }
}
