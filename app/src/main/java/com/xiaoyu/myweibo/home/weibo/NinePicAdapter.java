package com.xiaoyu.myweibo.home.weibo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.sina.weibo.sdk.api.share.Base;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.DensityUtil;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 16-8-31.
 */
public class NinePicAdapter extends BaseAdapter {

    private List<WeiBoDetailList.StatusesBean.PicUrlsBean> mPicList;
    private int mGridViewHeight;

    public NinePicAdapter(List<WeiBoDetailList.StatusesBean.PicUrlsBean> mPicList, int mGridViewHeight) {
        this.mPicList = mPicList;
        this.mGridViewHeight = mGridViewHeight;
    }

    @Override
    public int getCount() {
        return mPicList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        int height = (mGridViewHeight - 2 * DensityUtil.dip2px(8)) / 3;

        ImageView imageView = new ImageView(BaseApplication.context());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(height, height));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        LoadImage.getInstance().loadImage(mPicList.get(position).getThumbnail_pic(), imageView);

        return imageView;
    }
}
