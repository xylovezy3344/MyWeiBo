package com.xiaoyu.myweibo.home.weibo;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.DensityUtil;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

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

        LoadImage.getInstance().loadImage(mWeiBoDetailList.get(position).getUser().getAvatar_hd(),
                holder.ivUserIcon);
        holder.tvUserName.setText(mWeiBoDetailList.get(position).getUser().getName());
        holder.tvCreatedAt.setText(FormatUtils.formatTime(mWeiBoDetailList.get(position).getCreated_at()));
        holder.tvWeiBoText.setText(mWeiBoDetailList.get(position).getText());

        //图片数据
        List<WeiBoDetailList.StatusesBean.PicUrlsBean> pic_urls =
                mWeiBoDetailList.get(position).getPic_urls();

        Log.e(TAG, position + "--" + mWeiBoDetailList.get(position).getUser().getName() + "：" + pic_urls.size());

        if (pic_urls.size() == 0) {
            holder.gvNinePic.setVisibility(View.GONE);
        } else if (pic_urls.size() == 1) {
            holder.gvNinePic.setVisibility(View.GONE);
        } else {
            holder.gvNinePic.setVisibility(View.VISIBLE);
            //RecyclerView中嵌套，需要指定高度
            ViewGroup.LayoutParams params = holder.gvNinePic.getLayoutParams();
            params.width = mScreenWidth - 4 * DensityUtil.dip2px(8);

            if (pic_urls.size() <= 3) {
                params.height = params.width / 3;
            } else if (pic_urls.size() <= 6) {
                params.height = params.width / 3 * 2;
            } else {
                params.height = params.width;
            }

            holder.gvNinePic.setLayoutParams(params);
            holder.gvNinePic.setAdapter(new NinePicAdapter(pic_urls, params.width));
        }
    }

    @Override
    public int getItemCount() {
        return mWeiBoDetailList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvCreatedAt;
        TextView tvWeiBoText;
        //九宫格显示缩略图
        GridView gvNinePic;

        public MyViewHolder(View view)
        {
            super(view);
            ivUserIcon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
            tvWeiBoText = (TextView) view.findViewById(R.id.tv_weibo_text);
            gvNinePic = (GridView) view.findViewById(R.id.gv_nine_pic);
        }
    }

    /**
     * 给RecyclerView的item设置间距
     */
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = 0;
            }
        }

    }

    public List<String> getjiashuju() {

        List<String> list = new ArrayList<>();
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");
        list.add("http://img3.duitang.com/uploads/item/201201/10/20120110162201_2wh5i.thumb.700_0.jpg");

        return list;
    }


}
