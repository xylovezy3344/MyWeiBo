package com.xiaoyu.myweibo.presenter;

import com.xiaoyu.myweibo.activity.SingleWeiboActivity;
import com.xiaoyu.myweibo.bean.CommentList;
import com.xiaoyu.myweibo.contract.SingleWeiboContract;
import com.xiaoyu.myweibo.network.Comments;

import java.util.List;

import rx.Observer;

/**
 * 我的页面
 * Created by xiaoyu on 16-9-11.
 */
public class SingleWeiboPresenter implements SingleWeiboContract.Presenter {

    private SingleWeiboContract.View mSingleWeiboView;
    private List<CommentList.CommentsBean> mOldComments;
    //微博Api参数
    private long mSinceId = 0;
    private long mMaxId = 0;

    public SingleWeiboPresenter(SingleWeiboContract.View mSingleWeiboView) {
        this.mSingleWeiboView = mSingleWeiboView;
    }

    @Override
    public void getCommentList(final int type, long weiboId, List<CommentList.CommentsBean> oldCommentList) {

        mOldComments = oldCommentList;

        Observer<CommentList> observer = new Observer<CommentList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mSingleWeiboView.hideProgressDialog();
            }

            @Override
            public void onNext(CommentList commentList) {

                if (type == SingleWeiboActivity.FIRST_GET) {
                    mSinceId = commentList.getComments().get(0).getId();
                    mMaxId = commentList.getComments().get(commentList.getComments().size() - 1).getId();
                    mSingleWeiboView.showComments(commentList.getComments());

                    mSingleWeiboView.hideProgressDialog();
                }
                else if (type == SingleWeiboActivity.UP_REFRESH) {
                    mMaxId = commentList.getComments().get(commentList.getComments().size() - 1).getId();

                    pullUpdateData(commentList.getComments());
                    mSingleWeiboView.refreshComments(mOldComments);
                }
            }
        };

        if (type == SingleWeiboActivity.FIRST_GET) {
            mSingleWeiboView.showProgressDialog();
            Comments.getLatestComments(weiboId, observer);
        }
        else if (type == SingleWeiboActivity.UP_REFRESH) {
            if (mMaxId != 0) {
                Comments.getOldComments(weiboId, mMaxId, observer);
            } else {
                mSingleWeiboView.refreshComments(mOldComments);
            }
        }
    }

    private void pullUpdateData(List<CommentList.CommentsBean> list) {

        for (CommentList.CommentsBean commentsBean : list) {
            mOldComments.add(commentsBean);
        }
    }
}
