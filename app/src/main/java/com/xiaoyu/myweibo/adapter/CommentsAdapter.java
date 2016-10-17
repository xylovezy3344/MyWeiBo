package com.xiaoyu.myweibo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.utils.ImageUtils;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.CommentList;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关注人列表、粉丝列表适配器
 * Created by xiaoy on 16/9/17.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<CommentList.CommentsBean> mComments;

    public CommentsAdapter(List<CommentList.CommentsBean> commentList) {
        this.mComments = commentList;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(BaseApplication.context())
                .inflate(R.layout.single_weibo_comment_item, parent, false);

        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentsViewHolder holder, int position) {

        CommentList.CommentsBean commentsBean = mComments.get(position);

        LoadImageUtils.getInstance().loadImageAsBitmap(commentsBean.getUser()
                .getProfile_image_url(), holder.mIvIcon);
        holder.mTvName.setText(commentsBean.getUser().getName());
        holder.mTvTime.setText(FormatUtils.createdAt(commentsBean.getCreated_at()));
        holder.mTvComment.setText(commentsBean.getText());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_user_name)
        TextView mTvName;
        @BindView(R.id.tv_created_at)
        TextView mTvTime;
        @BindView(R.id.tv_comment_content)
        TextView mTvComment;

        CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
