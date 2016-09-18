package com.xiaoyu.myweibo.presenter;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.bean.WeiboDetailList;
import com.xiaoyu.myweibo.contract.WeiboContract;
import com.xiaoyu.myweibo.fragment.WeiboFragment;
import com.xiaoyu.myweibo.network.GetWeiboModel;

import java.util.List;

import rx.Observer;

/**
 * 主页面Presenter
 * Created by xiaoyu on 16-8-29.
 */
public class WeiboPresenter implements WeiboContract.Presenter {

    private WeiboContract.View mHomeView;
    private List<WeiboDetailList.StatusesBean> mOldWeiboList;

    //微博Api参数
    private long mSinceId = 0;
    private long mMaxId = 0;

    public WeiboPresenter(WeiboContract.View homeView) {
        this.mHomeView = homeView;
    }

    @Override
    public void getWeiBo(final int type, List<WeiboDetailList.StatusesBean> oldWeiboList) {

        mOldWeiboList = oldWeiboList;

        Observer<WeiboDetailList> observer = new Observer<WeiboDetailList>() {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e + "");
            }

            @Override
            public void onNext(WeiboDetailList weiBoDetailList) {

                if (type == WeiboFragment.FIRST_GET) {
                    mSinceId = weiBoDetailList.getSince_id();
                    mMaxId = weiBoDetailList.getMax_id();
                    mHomeView.showWeiBo(weiBoDetailList.getStatuses());

                    mHomeView.hideProgressDialog();
                }
                else if (type == WeiboFragment.DOWN_REFRESH) {
                    mSinceId = weiBoDetailList.getSince_id();
                    downUpdateData(weiBoDetailList.getStatuses());
                    mHomeView.refreshWeiBo(mOldWeiboList);
                }
                else if (type == WeiboFragment.UP_REFRESH) {
                    mMaxId = weiBoDetailList.getMax_id();

                    pullUpdateData(weiBoDetailList.getStatuses());
                    mHomeView.refreshWeiBo(mOldWeiboList);
                }
            }
        };

        if (type == WeiboFragment.FIRST_GET) {
            mHomeView.showProgressDialog();
            GetWeiboModel.getLatestWeiBo(observer);
        }
        else if (type == WeiboFragment.DOWN_REFRESH) {
            GetWeiboModel.getNewWeiBo(mSinceId, observer);
        }
        else if (type == WeiboFragment.UP_REFRESH) {

            GetWeiboModel.getOldWeiBo(mMaxId, observer);
        }
    }

    /**
     * 更新微博列表数据
     * @param list 刷新获取的微博集合
     */
    private void downUpdateData(List<WeiboDetailList.StatusesBean> list) {

        int keepWeiBoNum = 30;

        //微博总数
        int totalNum = mOldWeiboList.size() + list.size();

        //如果小于30条，直接往原数据上叠加数据
        //如果大于30条，叠加完数据后再从最后减去多于30的数据
        for (int i = 0; i < list.size(); i++) {
            mOldWeiboList.add(i, list.get(i));
        }

        if (totalNum >= keepWeiBoNum) {
            for (int i = 0; i < totalNum - keepWeiBoNum; i++) {
                mOldWeiboList.remove(mOldWeiboList.size() - 1);
            }
        }
    }

    private void pullUpdateData(List<WeiboDetailList.StatusesBean> list) {

        for (WeiboDetailList.StatusesBean statusesBean : list) {
            mOldWeiboList.add(statusesBean);
        }
    }
}
