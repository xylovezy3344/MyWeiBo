package com.xiaoyu.myweibo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImage;
import com.xiaoyu.myweibo.widget.WeiBoTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * 主页面微博列表适配器
 * Created by xiaoyu on 16-8-30.
 */
public class WeiBoListAdapter extends RecyclerView.Adapter<WeiBoListAdapter.MyViewHolder> implements BGANinePhotoLayout.Delegate {

    private static final String TAG = "WeiBoListAdapter：";

    private Activity mActivity;
    private List<WeiBoDetailList.StatusesBean> mWeiBoDetailList;
    private BGANinePhotoLayout mCurrentClickNpl;

    public WeiBoListAdapter(Activity activity, List<WeiBoDetailList.StatusesBean> mWeiBoDetailList) {
        this.mActivity = activity;
        this.mWeiBoDetailList = mWeiBoDetailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.context())
                .inflate(R.layout.weibo_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /**
         * 转发微博者
         */
        //头像
        LoadImage.getInstance().loadImageAsBitmap(mWeiBoDetailList.get(position)
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
        List<WeiBoDetailList.StatusesBean.PicUrlsBean> PicUrlsBeansF = mWeiBoDetailList
                .get(position).getPic_urls();
        ArrayList<String> friendPicUrls = new ArrayList<>();
        for (WeiBoDetailList.StatusesBean.PicUrlsBean bean : PicUrlsBeansF) {
            String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
            friendPicUrls.add(picUrl);
        }

        holder.friendNinePic.init(mActivity);
        holder.friendNinePic.setDelegate(this);
        holder.friendNinePic.setData(friendPicUrls);

        /**
         * 原微博，被转发者
         */
        if (mWeiBoDetailList.get(position).getRetweeted_status() != null) {

            holder.cvSourceWeiBo.setVisibility(View.VISIBLE);
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
            List<WeiBoDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean> PicUrlsBeansS =
                    mWeiBoDetailList.get(position).getRetweeted_status().getPic_urls();
            if (PicUrlsBeansS != null) {
                ArrayList<String> sourcePicUrls = new ArrayList<>();
                for (WeiBoDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean bean : PicUrlsBeansS) {
                    String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
                    sourcePicUrls.add(picUrl);
                }

                holder.sourceNinePic.init(mActivity);
                holder.sourceNinePic.setDelegate(this);
                holder.sourceNinePic.setData(sourcePicUrls);
            }

        } else {
            holder.cvSourceWeiBo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mWeiBoDetailList.size();
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public boolean onLongClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvCreatedAt;
        WeiBoTextView tvFriendWeiBoText;
        //九宫格显示缩略图
        BGANinePhotoLayout friendNinePic;

        WeiBoTextView tvSourceWeiBoText;
        CardView cvSourceWeiBo;
        //九宫格显示缩略图
        BGANinePhotoLayout sourceNinePic;

        public MyViewHolder(View view) {
            super(view);

            ivUserIcon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
            tvFriendWeiBoText = (WeiBoTextView) view.findViewById(R.id.tv_weibo_text_friend);
            friendNinePic = (BGANinePhotoLayout) view.findViewById(R.id.nine_pic_friend);

            tvSourceWeiBoText = (WeiBoTextView) view.findViewById(R.id.tv_weibo_text_source);
            cvSourceWeiBo = (CardView) view.findViewById(R.id.cv_source_weibo);
            sourceNinePic = (BGANinePhotoLayout) view.findViewById(R.id.nine_pic_source);
        }
    }

    private void photoPreviewWrapper() {

        if (mCurrentClickNpl == null) {
            return;
        }

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "MyWeiBoDownload");

        if (mCurrentClickNpl.getItemCount() == 1) {
            // 预览单张图片
            mActivity.startActivity(BGAPhotoPreviewActivity.newIntent(mActivity, downloadDir, mCurrentClickNpl.getCurrentClickItem()));
        } else if (mCurrentClickNpl.getItemCount() > 1) {
            // 预览多张图片
            mActivity.startActivity(BGAPhotoPreviewActivity.newIntent(mActivity, downloadDir, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
        }

    }
}
