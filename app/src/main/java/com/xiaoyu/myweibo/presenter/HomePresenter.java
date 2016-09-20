package com.xiaoyu.myweibo.presenter;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfoBean;
import com.xiaoyu.myweibo.contract.HomeContract;
import com.xiaoyu.myweibo.network.UserInfo;

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
                mHomeView.hideProgressDialog();
            }

            @Override
            public void onNext(UserInfoBean userInfoBean) {
                mHomeView.showUserInfo(userInfoBean);
            }
        });
    }
}
