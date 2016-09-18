package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.base.BasePresenter;
import com.xiaoyu.myweibo.base.BaseView;
import com.xiaoyu.myweibo.bean.WeiboDetailList;

import java.util.List;

/**
 * View和Presenter契约类
 */
public interface WeiboContract {

    interface View extends BaseView {

        void showWeiBo(List<WeiboDetailList.StatusesBean> list);
        void refreshWeiBo(List<WeiboDetailList.StatusesBean> list);
    }

    interface Presenter extends BasePresenter {

        void getWeiBo(int type, List<WeiboDetailList.StatusesBean> oldWeiboList);
    }
}
