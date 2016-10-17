package com.xiaoyu.myweibo.presenter;

import com.google.gson.Gson;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfoBean;
import com.xiaoyu.myweibo.contract.HomeContract;
import com.xiaoyu.myweibo.network.UserInfo;
import com.xiaoyu.myweibo.utils.CacheUtils;

import rx.Observer;

/**
 * 我的页面
 * Created by xiaoyu on 16-9-11.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    public HomePresenter(HomeContract.View mHomeView) {
        this.mHomeView = mHomeView;
    }

    @Override
    public void getUserInfo() {

        long uid = Long.parseLong(BaseApplication.accessToken().getUid());

        UserInfo.getUserInfo(uid, new Observer<UserInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Gson gson = new Gson();
                UserInfoBean userInfo = gson.fromJson(CacheUtils.get("user_info"), UserInfoBean.class);
                mHomeView.showUserInfo(userInfo);
                mHomeView.hideProgressDialog();
            }

            @Override
            public void onNext(UserInfoBean userInfoBean) {
                Gson gson = new Gson();
                CacheUtils.put(gson.toJson(userInfoBean), "user_info");
                mHomeView.showUserInfo(userInfoBean);
            }
        });
    }
}
