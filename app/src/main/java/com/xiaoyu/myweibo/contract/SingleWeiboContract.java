package com.xiaoyu.myweibo.contract;

import com.xiaoyu.myweibo.base.BasePresenter;
import com.xiaoyu.myweibo.base.BaseView;
import com.xiaoyu.myweibo.bean.CommentList;
import com.xiaoyu.myweibo.bean.UserInfoBean;
import com.xiaoyu.myweibo.bean.WeiboDetailList;

import java.util.List;

/**
 * 单条微博详情页面
 * Created by xiaoyu on 16-9-11.
 */
public interface SingleWeiboContract {

    interface View extends BaseView {
        void showComments(List<CommentList.CommentsBean> list);
        void refreshComments(List<CommentList.CommentsBean> list);
    }

    interface Presenter extends BasePresenter {
        void getCommentList(int type, long weiboId,
                            List<CommentList.CommentsBean> oldCommentList);
    }
}
