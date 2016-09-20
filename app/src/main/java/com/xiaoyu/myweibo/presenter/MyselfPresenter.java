package com.xiaoyu.myweibo.presenter;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfoBean;
import com.xiaoyu.myweibo.contract.MyselfContract;
import com.xiaoyu.myweibo.network.UserInfo;

import rx.Observer;

/**
 * 我的页面
 * Created by xiaoyu on 16-9-11.
 */
public class MyselfPresenter implements MyselfContract.Presenter {

    private MyselfContract.View mMyselfView;

    public MyselfPresenter(MyselfContract.View myselfView) {
        this.mMyselfView = myselfView;
    }

    @Override
    public void getUserInfo() {

        mMyselfView.showProgressDialog();

        long uid = Long.parseLong(BaseApplication.accessToken().getUid());

        UserInfo.getUserInfo(uid, new Observer<UserInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mMyselfView.hideProgressDialog();
            }

            @Override
            public void onNext(UserInfoBean userInfoBean) {
                mMyselfView.showUserInfo(userInfoBean);
                mMyselfView.hideProgressDialog();
            }
        });
    }
}
