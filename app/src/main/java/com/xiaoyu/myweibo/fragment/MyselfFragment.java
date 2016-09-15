package com.xiaoyu.myweibo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.activity.MyFriendActivity;
import com.xiaoyu.myweibo.activity.MyPhotoActivity;
import com.xiaoyu.myweibo.activity.MyUserInfoActivity;
import com.xiaoyu.myweibo.activity.MyWeiboActivity;
import com.xiaoyu.myweibo.bean.UserInfo;
import com.xiaoyu.myweibo.contract.MyselfContract;
import com.xiaoyu.myweibo.presenter.MyselfPresenter;
import com.xiaoyu.myweibo.utils.LoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGAImageView;

/**
 * 我的页面
 */
public class MyselfFragment extends Fragment implements MyselfContract.View {

    private static MyselfFragment mFragment;
    @BindView(R.id.iv_icon)
    BGAImageView mIvIcon;
    @BindView(R.id.tv_my_name)
    TextView mTvMyName;
    @BindView(R.id.tv_my_describe)
    TextView mTvMyDescribe;
    @BindView(R.id.tv_weibo_count)
    TextView mTvWeiboCount;
    @BindView(R.id.tv_friends_count)
    TextView mTvFriendsCount;
    @BindView(R.id.tv_followers_count)
    TextView mTvFollowersCount;
    private MyselfPresenter mMyselfPresenter;

    private MyselfFragment() {
        super();
    }

    public static MyselfFragment getInstance() {

        if (mFragment == null) {
            mFragment = new MyselfFragment();
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.myself_frag, container, false);
        ButterKnife.bind(this, view);

        mMyselfPresenter = new MyselfPresenter(this);

        mMyselfPresenter.getUserInfo();

        return view;
    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        //头像
        LoadImage.getInstance().loadImageAsBitmap(userInfo.getProfile_image_url(), mIvIcon);
        //昵称
        mTvMyName.setText(userInfo.getScreen_name());
        //简介
        if (TextUtils.isEmpty(userInfo.getDescription())) {
            mTvMyDescribe.setText("简介：暂无简介");
        } else {
            mTvMyDescribe.setText("简介：" + userInfo.getDescription());
        }
        //微博数
        mTvWeiboCount.setText(String.valueOf(userInfo.getStatuses_count()));
        // 关注数
        mTvFriendsCount.setText(String.valueOf(userInfo.getFriends_count()));
        // 粉丝数
        mTvFollowersCount.setText(String.valueOf(userInfo.getFollowers_count()));
    }

    //我的微博、我的赞、我的收藏，跳转到同一页面（微博列表）
    public static final String MY_WEIBO = "MY_WEIBO";
    public static final String MY_LIKE = "MY_LIKE";
    public static final String MY_COLLECTION = "MY_COLLECTION";
    //我的关注、我的粉丝，跳转到同一页面（用户列表）
    public static final String MY_FRIENDS = "MY_FRIENDS";
    public static final String MY_FOLLOWERS = "MY_FOLLOWERS";

    @OnClick({R.id.rl_user_info, R.id.ll_weibo_count, R.id.ll_friends_count, R.id.ll_followers_count, R.id.ll_my_photo, R.id.ll_my_like, R.id.ll_my_collection})
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            //用户信息
            case R.id.rl_user_info:
                intent = new Intent(getContext(), MyUserInfoActivity.class);
                break;
            //微博数量
            case R.id.ll_weibo_count:
                intent = new Intent(getContext(), MyWeiboActivity.class);
                intent.putExtra("tag", MY_WEIBO);
                break;
            // 我的赞
            case R.id.ll_my_like:
                intent = new Intent(getContext(), MyWeiboActivity.class);
                intent.putExtra("tag", MY_LIKE);
                break;
            // 我的收藏
            case R.id.ll_my_collection:
                intent = new Intent(getContext(), MyWeiboActivity.class);
                intent.putExtra("tag", MY_COLLECTION);
                break;
            //关注数量
            case R.id.ll_friends_count:
                intent = new Intent(getContext(), MyFriendActivity.class);
                intent.putExtra("tag", MY_FRIENDS);
                break;
            // 粉丝数量
            case R.id.ll_followers_count:
                intent = new Intent(getContext(), MyFriendActivity.class);
                intent.putExtra("tag", MY_FOLLOWERS);
                break;
            //我的相册
            default:
                intent = new Intent(getContext(), MyPhotoActivity.class);
                break;
        }

        startActivity(intent);
    }
}
