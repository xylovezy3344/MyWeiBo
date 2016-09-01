package com.xiaoyu.myweibo.home.weibo;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * 主页面Presenter
 * Created by xiaoyu on 16-8-29.
 */
public class WeiBoPresenter implements WeiBoContract.Presenter {

    private WeiBoContract.View mHomeView;

    //微博Api参数
    private long mSinceId = 0;
    private long mMaxId = 0;

    public WeiBoPresenter(WeiBoContract.View homeView) {
        this.mHomeView = homeView;
    }

    @Override
    public void getWeiBo(final int type) {

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
                } else if (type == WeiBoFragment.DOWN_REFRESH) {
                    mSinceId = weiBoDetailList.getSince_id();
                    mHomeView.refreshWeiBo(weiBoDetailList.getStatuses());
                } else if (type == WeiBoFragment.UP_REFRESH) {
                    mMaxId = weiBoDetailList.getMax_id();
                    mHomeView.showWeiBo(weiBoDetailList.getStatuses());
                }
            }
        };

        if (type == WeiBoFragment.FIRST_GET) {
            GetWeiBoModel.getLatestWeiBo(observer);
        } else if (type == WeiBoFragment.DOWN_REFRESH) {
            GetWeiBoModel.getNewWeiBo(mSinceId, observer);
        } else if (type == WeiBoFragment.UP_REFRESH) {
            GetWeiBoModel.getOldWeiBo(mMaxId, observer);
        }

        /**
         * 假数据测试
         */

//        if (type == HomeActivity.FIRST_GET) {
//            WeiBoDetailList weiBoDetailList = jiashuju();
//            mHomeView.showWeiBo(weiBoDetailList.getStatuses());
//        } else if (type == HomeActivity.DOWN_REFRESH) {
//            WeiBoDetailList weiBoDetailList = jiashujunNew();
//            mHomeView.refreshWeiBo(weiBoDetailList.getStatuses());
//        } else if (type == HomeActivity.UP_REFRESH) {
//            WeiBoDetailList weiBoDetailList = jiashujunNew();
//            mHomeView.refreshWeiBo(weiBoDetailList.getStatuses());
//        }
    }

    //造假数据测试1-10
    private WeiBoDetailList jiashuju() {

        List<WeiBoDetailList.StatusesBean> StatusesBeans =
                new ArrayList<WeiBoDetailList.StatusesBean>();

        for (int i = 0; i < 10; i++) {
            WeiBoDetailList.StatusesBean statusesBean1 = new WeiBoDetailList.StatusesBean();
            WeiBoDetailList.StatusesBean.UserBean user1 = new WeiBoDetailList.StatusesBean.UserBean();
            user1.setName(i + "");
            statusesBean1.setUser(user1);
            StatusesBeans.add(statusesBean1);
        }
        WeiBoDetailList list = new WeiBoDetailList();
        list.setStatuses(StatusesBeans);

        return list;
    }

    //造假数据测试1-3
    private WeiBoDetailList jiashujunNew() {

        List<WeiBoDetailList.StatusesBean> StatusesBeans =
                new ArrayList<WeiBoDetailList.StatusesBean>();

        for (int i = 0; i < 10; i++) {
            WeiBoDetailList.StatusesBean statusesBean1 = new WeiBoDetailList.StatusesBean();
            WeiBoDetailList.StatusesBean.UserBean user1 = new WeiBoDetailList.StatusesBean.UserBean();
            user1.setName('a' + i + "");
            statusesBean1.setUser(user1);
            StatusesBeans.add(statusesBean1);
        }
        WeiBoDetailList list = new WeiBoDetailList();
        list.setStatuses(StatusesBeans);

        return list;
    }
}
