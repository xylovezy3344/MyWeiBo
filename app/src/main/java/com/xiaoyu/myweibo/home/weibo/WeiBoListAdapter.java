package com.xiaoyu.myweibo.home.weibo;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.BitmapBean;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.DensityUtil;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主页面微博列表适配器
 * Created by xiaoyu on 16-8-30.
 */
public class WeiBoListAdapter extends RecyclerView.Adapter<WeiBoListAdapter.MyViewHolder> {

    private static final String TAG = "WeiBoListAdapter：";

    private List<WeiBoDetailList.StatusesBean> mWeiBoDetailList;
    private int mScreenWidth;

    public WeiBoListAdapter(List<WeiBoDetailList.StatusesBean> mWeiBoDetailList, int mScreenWidth) {
        this.mWeiBoDetailList = mWeiBoDetailList;
        this.mScreenWidth = mScreenWidth;
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
        holder.tvFriendWeiBoText.setText(mWeiBoDetailList.get(position).getText());
        //微博图片内容
        List<WeiBoDetailList.StatusesBean.PicUrlsBean> PicUrlsBeansF = mWeiBoDetailList
                .get(position).getPic_urls();
        List<String> friendPicUrls = new ArrayList<>();
        for (WeiBoDetailList.StatusesBean.PicUrlsBean bean : PicUrlsBeansF) {
            friendPicUrls.add(bean.getThumbnail_pic());
        }

        setNinePic(friendPicUrls, holder.gvFriendNinePic, holder.flFriendOnePic);

        /**
         * 原微博，被转发者
         */
        if (mWeiBoDetailList.get(position).getRetweeted_status() != null) {

            holder.cvSourceWeiBo.setVisibility(View.VISIBLE);
            //微博文字内容
            String sourceText = "@" + mWeiBoDetailList.get(position).getRetweeted_status().getUser()
                    .getName() + "：" + mWeiBoDetailList.get(position).getRetweeted_status().getText();
            holder.tvSourceWeiBoText.setText(sourceText);
            //微博图片内容
            List<WeiBoDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean> PicUrlsBeansS =
                    mWeiBoDetailList.get(position).getRetweeted_status().getPic_urls();
            List<String> sourcePicUrls = new ArrayList<>();
            for (WeiBoDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean bean : PicUrlsBeansS) {
                sourcePicUrls.add(bean.getThumbnail_pic());
            }

            setNinePic(sourcePicUrls, holder.gvSourceNinePic, holder.flSourceOnePic);
        } else {
            holder.cvSourceWeiBo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mWeiBoDetailList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvCreatedAt;
        TextView tvFriendWeiBoText;
        //九宫格显示缩略图
        GridView gvFriendNinePic;
        FrameLayout flFriendOnePic;
        //ImageView ivFriendOnePic;
        //ImageView ivFriendGifTag;

        TextView tvSourceWeiBoText;
        CardView cvSourceWeiBo;
        //九宫格显示缩略图
        GridView gvSourceNinePic;
        FrameLayout flSourceOnePic;
        //ImageView ivSourceOnePic;
        //ImageView ivSourceGifTag;


        public MyViewHolder(View view) {
            super(view);

            ivUserIcon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
            tvFriendWeiBoText = (TextView) view.findViewById(R.id.tv_weibo_text_friend);
            gvFriendNinePic = (GridView) view.findViewById(R.id.gv_nine_pic_friend);
            flFriendOnePic = (FrameLayout) view.findViewById(R.id.fl_one_pic_friend);
            //ivFriendOnePic = (ImageView) view.findViewById(R.id.iv_one_pic_friend);
            //ivFriendGifTag = (ImageView) view.findViewById(R.id.iv_friend_gif_tag);

            tvSourceWeiBoText = (TextView) view.findViewById(R.id.tv_weibo_text_source);
            gvSourceNinePic = (GridView) view.findViewById(R.id.gv_nine_pic_source);
            cvSourceWeiBo = (CardView) view.findViewById(R.id.cv_source_weibo);
            flSourceOnePic = (FrameLayout) view.findViewById(R.id.fl_one_pic_source);
            //ivSourceOnePic = (ImageView) view.findViewById(R.id.iv_one_pic_source);
            //ivSourceGifTag = (ImageView) view.findViewById(R.id.iv_source_gif_tag);
        }
    }

    private void setNinePic(List<String> picUrls, GridView gridView, final FrameLayout frameLayout) {

        if (picUrls.size() == 0) {
            gridView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
        } else if (picUrls.size() == 1) {

            //集合中拿到地址是小图（thumbnail），需要将地址中thumbnail替换成bmiddle变成中等大小，
            //或者替换成large变成大图
            final String picUrl = picUrls.get(0).replace("thumbnail", "bmiddle");
            gridView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

            //子线程中拿到图片宽高，主线程中设置ImageView
            Observable.create(new Observable.OnSubscribe<BitmapBean>() {
                @Override
                public void call(Subscriber<? super BitmapBean> subscriber) {
                    Bitmap bitmap = LoadImage.getInstance().loadBitmap(picUrl);
                    if (bitmap != null) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();

                        BitmapBean bean = new BitmapBean(width, height);

                        subscriber.onNext(bean);
                    }
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BitmapBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(BitmapBean s) {

                            ViewGroup.LayoutParams params = frameLayout.getLayoutParams();

                            if (picUrl.endsWith("gif")) {
                                frameLayout.getChildAt(1).setVisibility(View.VISIBLE);
                                params.width = mScreenWidth / 2;
                                params.height = mScreenWidth / 2;
                            } else {
                                frameLayout.getChildAt(1).setVisibility(View.GONE);
                                if (s.getWidth() > s.getHeight()) {
                                    params.width = mScreenWidth / 3 * 2;
                                    params.height = mScreenWidth / 2;
                                } else {
                                    params.width = mScreenWidth / 2;
                                    params.height = mScreenWidth / 3 * 2;
                                }
                            }
                            frameLayout.setLayoutParams(params);
                            LoadImage.getInstance().loadImageAsBitmap(picUrl, (ImageView) frameLayout.getChildAt(0));
                        }
                    });
        } else {
            gridView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            //view.setNumColumns(3);
            //RecyclerView中嵌套，需要指定高度
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.width = mScreenWidth - 4 * DensityUtil.dip2px(8);

            if (picUrls.size() <= 3) {
                params.height = params.width / 3;
            } else if (picUrls.size() <= 6) {
                params.height = params.width / 3 * 2;
            } else {
                params.height = params.width;
            }

            gridView.setLayoutParams(params);
            gridView.setAdapter(new NinePicAdapter(picUrls, params.width));
        }
    }
}
