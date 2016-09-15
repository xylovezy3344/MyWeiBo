package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseActivity;
import com.xiaoyu.myweibo.fragment.DiscoveryFragment;
import com.xiaoyu.myweibo.fragment.MessageFragment;
import com.xiaoyu.myweibo.fragment.MyselfFragment;
import com.xiaoyu.myweibo.fragment.WeiBoFragment;
import com.xiaoyu.myweibo.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private WeiBoFragment mWeiBoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_act);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        //ToolBar标题设置成登陆账号昵称
        mCollapsingToolbar.setTitle("XXXXXX");

        //填充fragment
        mWeiBoFragment = (WeiBoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fl_weibo_list);
        if (mWeiBoFragment == null) {
            mWeiBoFragment = new WeiBoFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mWeiBoFragment, R.id.fl_weibo_list);
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
            mWeiBoFragment.refreshForActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //填充微博页面
            ActivityUtils.replaceFragment(getSupportFragmentManager(),
                    mWeiBoFragment, R.id.fl_weibo_list);
        } else if (id == R.id.nav_message) {
            //填充消息页面
            MessageFragment messageFragment = MessageFragment.getInstance();
            ActivityUtils.replaceFragment(getSupportFragmentManager(),
                    messageFragment, R.id.fl_weibo_list);

        } else if (id == R.id.nav_discover) {
            //填充发现页面
            DiscoveryFragment discoveryFragment = DiscoveryFragment.getInstance();
            ActivityUtils.replaceFragment(getSupportFragmentManager(),
                    discoveryFragment, R.id.fl_weibo_list);

        } else if (id == R.id.nav_myself) {
            //填充我的页面
            MyselfFragment myselfFragment = MyselfFragment.getInstance();
            ActivityUtils.replaceFragment(getSupportFragmentManager(),
                    myselfFragment, R.id.fl_weibo_list);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
