package com.xiaoyu.myweibo.presenter;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.UserInfo;
import com.xiaoyu.myweibo.contract.MyselfContract;
import com.xiaoyu.myweibo.network.GetUserInfo;

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

        GetUserInfo.getUserInfo(uid, new Observer<UserInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfo userInfo) {
                mMyselfView.showUserInfo(userInfo);
                mMyselfView.hideProgressDialog();
            }
        });
    }
}
