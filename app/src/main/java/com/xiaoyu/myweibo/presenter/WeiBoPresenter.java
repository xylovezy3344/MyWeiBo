package com.xiaoyu.myweibo.presenter;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.contract.WeiBoContract;
import com.xiaoyu.myweibo.network.GetWeiBoModel;
import com.xiaoyu.myweibo.ui.WeiBoFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * 主页面Presenter
 * Created by xiaoyu on 16-8-29.
 */
public class WeiBoPresenter implements WeiBoContract.Presenter {

    private WeiBoContract.View mHomeView;
    private List<WeiBoDetailList.StatusesBean> mOldWeiboList;

    //微博Api参数
    private long mSinceId = 0;
    private long mMaxId = 0;

    public WeiBoPresenter(WeiBoContract.View homeView) {
        this.mHomeView = homeView;
    }

    @Override
    public void getWeiBo(final int type, List<WeiBoDetailList.StatusesBean> oldWeiboList) {

        mOldWeiboList = oldWeiboList;

        Observer<WeiBoDetailList> observer = new Observer<WeiBoDetailList>() {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e + "");
            }

            @Override
            public void onNext(WeiBoDetailList weiBoDetailList) {

                if (type == WeiBoFragment.FIRST_GET) {
                    mSinceId = weiBoDetailList.getSince_id();
                    mMaxId = weiBoDetailList.getMax_id();
                    mHomeView.showWeiBo(weiBoDetailList.getStatuses());
                }
                else if (type == WeiBoFragment.DOWN_REFRESH) {
                    mSinceId = weiBoDetailList.getSince_id();
                    downUpdateData(weiBoDetailList.getStatuses());
                    mHomeView.refreshWeiBo(mOldWeiboList);
                }
                else if (type == WeiBoFragment.UP_REFRESH) {
                    mMaxId = weiBoDetailList.getMax_id();

                    pullUpdateData(weiBoDetailList.getStatuses());
                    mHomeView.refreshWeiBo(mOldWeiboList);
                }
            }
        };

        if (type == WeiBoFragment.FIRST_GET) {
            GetWeiBoModel.getLatestWeiBo(observer);
        }
        else if (type == WeiBoFragment.DOWN_REFRESH) {
            GetWeiBoModel.getNewWeiBo(mSinceId, observer);
        }
        else if (type == WeiBoFragment.UP_REFRESH) {

            GetWeiBoModel.getOldWeiBo(mMaxId, observer);
        }
    }

    /**
     * 更新微博列表数据
     * @param list 刷新获取的微博集合
     */
    private void downUpdateData(List<WeiBoDetailList.StatusesBean> list) {

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

    private void pullUpdateData(List<WeiBoDetailList.StatusesBean> list) {

        for (WeiBoDetailList.StatusesBean statusesBean : list) {
            mOldWeiboList.add(statusesBean);
        }
    }
}
