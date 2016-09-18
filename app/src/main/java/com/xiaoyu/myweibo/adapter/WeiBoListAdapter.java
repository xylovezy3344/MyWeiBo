package com.xiaoyu.myweibo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiboDetailList;
import com.xiaoyu.myweibo.utils.AppManager;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImageUtils;
import com.xiaoyu.myweibo.utils.PreviewPhoto;
import com.xiaoyu.myweibo.widget.WeiboTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * 主页面微博列表适配器
 * Created by xiaoyu on 16-8-30.
 */
public class WeiboListAdapter extends RecyclerView.Adapter<WeiboListAdapter.WeiboListViewHolder> implements BGANinePhotoLayout.Delegate {

    private static final String TAG = "WeiboListAdapter：";

    private List<WeiboDetailList.StatusesBean> mWeiBoDetailList;

    public WeiboListAdapter(List<WeiboDetailList.StatusesBean> mWeiBoDetailList) {
        this.mWeiBoDetailList = mWeiBoDetailList;
    }

    @Override
    public WeiboListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.context())
                .inflate(R.layout.home_weibo_item, parent, false);

        return new WeiboListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeiboListViewHolder holder, int position) {
        /**
         * 转发微博者
         */
        //头像
        LoadImageUtils.getInstance().loadImageAsBitmap(mWeiBoDetailList.get(position)
                .getUser().getAvatar_hd(), holder.ivUserIcon);
        //用户名
        holder.tvUserName.setText(mWeiBoDetailList.get(position).getUser().getName());
        //发微博时间
        holder.tvCreatedAt.setText(FormatUtils.formatTime(mWeiBoDetailList.get(position)
                .getCreated_at()));
        //微博文字内容
        String friendText = mWeiBoDetailList.get(position).getText();
        holder.tvFriendWeiBoText.setText(friendText);
        //微博图片内容
        List<WeiboDetailList.StatusesBean.PicUrlsBean> PicUrlsBeansF = mWeiBoDetailList
                .get(position).getPic_urls();
        ArrayList<String> friendPicUrls = new ArrayList<>();
        for (WeiboDetailList.StatusesBean.PicUrlsBean bean : PicUrlsBeansF) {
            String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
            friendPicUrls.add(picUrl);
        }

        holder.friendNinePic.init(AppManager.getAppManager().currentActivity());
        holder.friendNinePic.setDelegate(this);
        holder.friendNinePic.setData(friendPicUrls);

        /**
         * 原微博，被转发者
         */
        if (mWeiBoDetailList.get(position).getRetweeted_status() != null) {

            holder.llSourceWeiBo.setVisibility(View.VISIBLE);
            //微博文字内容
            String sourceText;
            if (mWeiBoDetailList.get(position).getRetweeted_status().getUser() != null) {
                sourceText = "@" + mWeiBoDetailList.get(position).getRetweeted_status().getUser()
                        .getName() + ":" + mWeiBoDetailList.get(position).getRetweeted_status().getText();
            } else {
                sourceText = mWeiBoDetailList.get(position).getRetweeted_status().getText();
            }


            holder.tvSourceWeiBoText.setText(sourceText);
            //微博图片内容
            List<WeiboDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean> PicUrlsBeansS =
                    mWeiBoDetailList.get(position).getRetweeted_status().getPic_urls();
            if (PicUrlsBeansS != null) {
                ArrayList<String> sourcePicUrls = new ArrayList<>();
                for (WeiboDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean bean : PicUrlsBeansS) {
                    String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
                    sourcePicUrls.add(picUrl);
                }

                holder.sourceNinePic.init(AppManager.getAppManager().currentActivity());
                holder.sourceNinePic.setDelegate(this);
                holder.sourceNinePic.setData(sourcePicUrls);
            }

        } else {
            holder.llSourceWeiBo.setVisibility(View.GONE);
        }

        /**
         * 转发 评论 赞
         */
        int repostsCount = mWeiBoDetailList.get(position).getReposts_count();
        int commentsCount = mWeiBoDetailList.get(position).getComments_count();
        int attitudesCount = mWeiBoDetailList.get(position).getAttitudes_count();
        if (repostsCount == 0) {
            holder.tvReposts.setText("转发");
        } else {
            holder.tvReposts.setText(repostsCount + "");
        }
        if (commentsCount == 0) {
            holder.tvComment.setText("评论");
        } else {
            holder.tvComment.setText(commentsCount + "");
        }
        if (attitudesCount == 0) {
            holder.tvAttitudes.setText("赞");
        } else {
            holder.tvAttitudes.setText(attitudesCount + "");
        }

    }

    @Override
    public int getItemCount() {
        return mWeiBoDetailList.size();
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

        if (ninePhotoLayout != null) {

            if (ninePhotoLayout.getItemCount() == 1) {
                // 预览单张图片
                PreviewPhoto.previewPhoto(ninePhotoLayout.getCurrentClickItem());
            } else if (ninePhotoLayout.getItemCount() > 1) {
                // 预览多张图片
                PreviewPhoto.previewPhotos(ninePhotoLayout.getData(), ninePhotoLayout.getCurrentClickItemPosition());
            }
        }
    }

    @Override
    public boolean onLongClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        return false;
    }

    class WeiboListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_icon)
        ImageView ivUserIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_created_at)
        TextView tvCreatedAt;
        @BindView(R.id.tv_weibo_text_friend)
        WeiboTextView tvFriendWeiBoText;
        //九宫格显示缩略图
        @BindView(R.id.nine_pic_friend)
        BGANinePhotoLayout friendNinePic;

        @BindView(R.id.tv_weibo_text_source)
        WeiboTextView tvSourceWeiBoText;
        @BindView(R.id.ll_source_weibo)
        LinearLayout llSourceWeiBo;
        //九宫格显示缩略图
        @BindView(R.id.nine_pic_source)
        BGANinePhotoLayout sourceNinePic;

        //转发 评论 赞
        @BindView(R.id.ll_reposts)
        LinearLayout llReposts;
        @BindView(R.id.tv_reposts)
        TextView tvReposts;
        @BindView(R.id.ll_comments)
        LinearLayout llComments;
        @BindView(R.id.tv_comments)
        TextView tvComment;
        @BindView(R.id.ll_attitudes)
        LinearLayout llAttitudes;
        @BindView(R.id.iv_attitudes)
        ImageView ivAttitudes;
        @BindView(R.id.tv_attitudes)
        TextView tvAttitudes;

        public WeiboListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
