package com.xiaoyu.myweibo.presenter;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.activity.MyRelationActivity;
import com.xiaoyu.myweibo.bean.RelationInfoList;
import com.xiaoyu.myweibo.contract.MyRelationContract;
import com.xiaoyu.myweibo.fragment.MyselfFragment;
import com.xiaoyu.myweibo.network.UserRelation;

import java.util.List;

import rx.Observer;

/**
 *
 * Created by xiaoy on 16/9/17.
 */
public class MyRelationPresenter implements MyRelationContract.Presenter {

    private MyRelationContract.View mMyRelationView;
    private List<RelationInfoList.UsersBean> mOldList;
    private int mCursor;

    public MyRelationPresenter(MyRelationContract.View myRelationView) {
        mMyRelationView = myRelationView;
    }

    @Override
    public void getRelationList(final int type, List<RelationInfoList.UsersBean> oldList, final String tag) {

        mMyRelationView.showProgressDialog();

        mOldList =  oldList;

        Observer<RelationInfoList> observer = new Observer<RelationInfoList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mMyRelationView.hideProgressDialog();
            }

            @Override
            public void onNext(RelationInfoList list) {

                if (type == MyRelationActivity.FIRST_GET) {
                    mMyRelationView.showRelationList(list.getUsers());
                }
                else if (type == MyRelationActivity.UP_REFRESH) {
                    pullUpdateData(list.getUsers());
                    mMyRelationView.loadMore(mOldList);
                }

                mMyRelationView.hideProgressDialog();
            }
        };

        if (type == MyRelationActivity.FIRST_GET) {

            mCursor = 0;

            if (tag.equals(MyselfFragment.MY_FRIENDS)) {
                UserRelation.getFriendList(mCursor, observer);
            } else {
                UserRelation.getFollowerList(mCursor, observer);
            }
        }
        else if (type == MyRelationActivity.UP_REFRESH) {

            mCursor = mOldList.size();

            if (tag.equals(MyselfFragment.MY_FRIENDS)) {
                UserRelation.getFriendList(mCursor, observer);
            } else {
                UserRelation.getFollowerList(mCursor, observer);
            }
        }
    }

    private void pullUpdateData(List<RelationInfoList.UsersBean> newList) {
        for (RelationInfoList.UsersBean usersBean : newList) {
            mOldList.add(usersBean);
        }
    }
}
