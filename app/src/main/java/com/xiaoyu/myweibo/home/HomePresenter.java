package com.xiaoyu.myweibo.home;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * 主页面Presenter
 * Created by xiaoyu on 16-8-29.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    //微博Api参数
    private long mSinceId = 0;
    private long mMaxId = 0;

    public HomePresenter(HomeContract.View homeView) {
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

                if (type == HomeActivity.FIRST_GET) {

                    mSinceId = weiBoDetailList.getSince_id();
                    mMaxId = weiBoDetailList.getMax_id();
                    mHomeView.showWeiBo(weiBoDetailList.getStatuses());

                } else if (type == HomeActivity.DOWN_REFRESH) {

                    mSinceId = weiBoDetailList.getSince_id();
                    mHomeView.refreshWeiBo(weiBoDetailList.getStatuses());

                } else if (type == HomeActivity.UP_REFRESH) {

                    mMaxId = weiBoDetailList.getMax_id();
                    mHomeView.showWeiBo(weiBoDetailList.getStatuses());

                }

            }
        };

        if (type == HomeActivity.FIRST_GET) {

            GetWeiBoModel.getLatestWeiBo(observer);

        } else if (type == HomeActivity.DOWN_REFRESH) {

            GetWeiBoModel.getNewWeiBo(mSinceId, observer);

        } else if (type == HomeActivity.UP_REFRESH) {

            GetWeiBoModel.getOldWeiBo(mMaxId, observer);

        }
    }

    //造假数据测试
    private WeiBoDetailList jiashuju(String a) {
        List<WeiBoDetailList.StatusesBean> StatusesBeans =
                new ArrayList<WeiBoDetailList.StatusesBean>();
        WeiBoDetailList.StatusesBean statusesBean1 = new WeiBoDetailList.StatusesBean();
        WeiBoDetailList.StatusesBean.UserBean user1 = new WeiBoDetailList.StatusesBean.UserBean();
        user1.setName(a);
        statusesBean1.setUser(user1);
        StatusesBeans.add(statusesBean1);
        WeiBoDetailList list = new WeiBoDetailList();
        list.setStatuses(StatusesBeans);

        return list;
    }
}
