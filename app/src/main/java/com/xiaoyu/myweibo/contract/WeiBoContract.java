package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import java.util.List;

/**
 * View和Presenter契约类
 */
public interface WeiBoContract {

    interface View  {

        void showWeiBo(List<WeiBoDetailList.StatusesBean> list);
        void refreshWeiBo(List<WeiBoDetailList.StatusesBean> list);
    }

    interface Presenter {

        void getWeiBo(int type, List<WeiBoDetailList.StatusesBean> oldWeiboList);
    }
}
