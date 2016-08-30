package com.xiaoyu.myweibo.home;

import com.xiaoyu.myweibo.bean.WeiBoDetailList;

import java.util.List;

/**
 * View和Presenter契约类
 */
public interface HomeContract {

    interface View  {

        void showWeiBo(List<WeiBoDetailList.StatusesBean> list);
        void refreshWeiBo(List<WeiBoDetailList.StatusesBean> list);
    }

    interface Presenter {

        void getWeiBo(int type);
    }
}
