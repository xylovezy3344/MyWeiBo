package com.xiaoyu.myweibo.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rv_weibo_detail)
    RecyclerView mRvWeiboDetail;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private WeiBoListAdapter mAdapter;
    private HomePresenter mHomePresenter;
    private List<WeiBoDetailList.StatusesBean> mWeiBoDetailList;

    //表示首次请求数据
    public static int FIRST_GET = 0;
    //上拉加载更多
    public static int UP_REFRESH = 1;
    //下拉刷新
    public static int DOWN_REFRESH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_act);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        //ToolBar标题设置成登陆账号昵称
        mCollapsingToolbar.setTitle("朕的昵称什么鬼");

        //设置布局管理器
        mRvWeiboDetail.setLayoutManager(new LinearLayoutManager(this));
        //设置Item增加、移除动画
        mRvWeiboDetail.setItemAnimator(new DefaultItemAnimator());

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mHomePresenter.getWeiBo(DOWN_REFRESH);
            }
        });
    }

    protected void initData() {
        mHomePresenter = new HomePresenter(this);
        //首次请求微博数据
        mHomePresenter.getWeiBo(FIRST_GET);
    }

    @Override
    public void showWeiBo(List<WeiBoDetailList.StatusesBean> list) {
        mWeiBoDetailList = list;
        //设置adapter
        mAdapter = new WeiBoListAdapter(mWeiBoDetailList);
        mRvWeiboDetail.setAdapter(mAdapter);
    }

    @Override
    public void refreshWeiBo(List<WeiBoDetailList.StatusesBean> list) {
        updateData(list);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
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
                mWeiBoDetailList.remove(mWeiBoDetailList.size() - 1 - i);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            //点击刷新按钮，等同于下拉刷新
            mHomePresenter.getWeiBo(DOWN_REFRESH);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
