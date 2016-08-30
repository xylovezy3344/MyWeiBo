package com.xiaoyu.myweibo.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.List;

/**
 * 主页面微博列表适配器
 * Created by xiaoyu on 16-8-30.
 */
public class WeiBoListAdapter extends RecyclerView.Adapter<WeiBoListAdapter.MyViewHolder> {

    private List<WeiBoDetailList.StatusesBean> mWeiBoDetailList;

    public WeiBoListAdapter(List<WeiBoDetailList.StatusesBean> mWeiBoDetailList) {
        this.mWeiBoDetailList = mWeiBoDetailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.context())
                .inflate(R.layout.home_item_weibo_detail, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LoadImage.loadImage(mWeiBoDetailList.get(position).getUser().getAvatar_hd(),
                holder.ivUserIcon);
        holder.tvUserName.setText(mWeiBoDetailList.get(position).getUser().getName());
        holder.tvCreatedAt.setText(mWeiBoDetailList.get(position).getCreated_at());
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

        public MyViewHolder(View view)
        {
            super(view);
            ivUserIcon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
        }
    }
}
