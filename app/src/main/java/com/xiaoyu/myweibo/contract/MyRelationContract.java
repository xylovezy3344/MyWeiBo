package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.bean.RelationInfoList;

import java.util.List;

/**
 * 我的主页
 * Created by xiaoyu on 16-9-11.
 */
public interface MyRelationContract {

    interface View {
        void showRelationList(List<RelationInfoList.UsersBean> list);
        void loadMore(List<RelationInfoList.UsersBean> list);
    }

    interface Presenter {
        void getRelationList(int type, List<RelationInfoList.UsersBean> oldList, String tag);
    }
}
