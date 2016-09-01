package com.xiaoyu.myweibo.home.weibo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.utils.DensityUtil;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.List;


/**
 * 九宫格图片适配器
 * Created by xiaoyu on 16-8-31.
 */
public class NinePicAdapter extends BaseAdapter {

    private List<String> mPicUrlList;
    private int mGridViewHeight;

    public NinePicAdapter(List<String> mPicUrlList, int mGridViewHeight) {
        this.mPicUrlList = mPicUrlList;
        this.mGridViewHeight = mGridViewHeight;
    }

    @Override
    public int getCount() {
        return mPicUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicUrlList.get(position);
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

        //集合中拿到地址是小图（thumbnail），需要将地址中thumbnail替换成bmiddle变成中等大小，
        //或者替换成large变成大图
        String picUrl = mPicUrlList.get(position).replace("thumbnail", "bmiddle");
        LoadImage.getInstance().loadImageAsBitmap(picUrl, imageView);

        return imageView;
    }
}
