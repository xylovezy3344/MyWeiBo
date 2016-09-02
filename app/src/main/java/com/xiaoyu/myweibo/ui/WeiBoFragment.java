package com.xiaoyu.myweibo.ui;

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

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.adapter.WeiBoListAdapter;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.contract.WeiBoContract;
import com.xiaoyu.myweibo.presenter.WeiBoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeiBoFragment extends Fragment implements WeiBoContract.View {

    @BindView(R.id.rv_weibo_detail)
    RecyclerView mRvWeiboDetail;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private WeiBoListAdapter mAdapter;
    private WeiBoPresenter mWeiBoPresenter;
    private List<WeiBoDetailList.StatusesBean> mWeiBoDetailList;

    //表示首次请求数据
    public static int FIRST_GET = 0;
    //上拉加载更多
    public static int UP_REFRESH = 1;
    //下拉刷新
    public static int DOWN_REFRESH = 2;

    private int mScreenWidth;

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
        View view = inflater.inflate(R.layout.weibo_frag, container, false);
        ButterKnife.bind(this, view);

        //设置布局管理器
        mRvWeiboDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置Item增加、移除动画
        mRvWeiboDetail.setItemAnimator(new DefaultItemAnimator());

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mWeiBoPresenter.getWeiBo(DOWN_REFRESH);
            }
        });

        mWeiBoPresenter = new WeiBoPresenter(this);
        //首次请求微博数据
        mWeiBoPresenter.getWeiBo(FIRST_GET);

        return view;
    }

    @Override
    public void showWeiBo(List<WeiBoDetailList.StatusesBean> list) {
        mWeiBoDetailList = list;
        //设置adapter
        mAdapter = new WeiBoListAdapter(getActivity(), mWeiBoDetailList);
        mRvWeiboDetail.setAdapter(mAdapter);
    }

    @Override
    public void refreshWeiBo(List<WeiBoDetailList.StatusesBean> list) {
        updateData(list);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    /**
     * 供HomeActivity调用，菜单刷新按钮实现
     */
    public void refreshForActivity() {
        mRefreshLayout.setRefreshing(true);
        mWeiBoPresenter.getWeiBo(DOWN_REFRESH);
    }

    /**
     * 更新微博列表数据
     * 默认设置保留30条微博（keepWeiBoNum = 30）
     *
     * @param list 刷新获取的微博集合
     */
    private void updateData(List<WeiBoDetailList.StatusesBean> list) {

        int keepWeiBoNum = 30;

        //微博总数
        int totalNum = mWeiBoDetailList.size() + list.size();

        //如果小于30条，直接往原数据上叠加数据
        //如果大于30条，叠加完数据后再从最后减去多于30的数据
        for (int i = 0; i < list.size(); i++) {
            mWeiBoDetailList.add(i, list.get(i));
        }

        if (totalNum >= keepWeiBoNum) {
            for (int i = 0; i < totalNum - keepWeiBoNum; i++) {
                mWeiBoDetailList.remove(mWeiBoDetailList.size() - 1);
            }
        }
    }

}
