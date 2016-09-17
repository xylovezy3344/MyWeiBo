package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.bean.WeiboDetailList;

import java.util.List;

/**
 * View和Presenter契约类
 */
public interface WeiboContract {

    interface View  {

        void showWeiBo(List<WeiboDetailList.StatusesBean> list);
        void refreshWeiBo(List<WeiboDetailList.StatusesBean> list);
    }

    interface Presenter {

        void getWeiBo(int type, List<WeiboDetailList.StatusesBean> oldWeiboList);
    }
}
