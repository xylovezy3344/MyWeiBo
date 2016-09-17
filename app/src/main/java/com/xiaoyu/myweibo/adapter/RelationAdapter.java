package com.xiaoyu.myweibo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.RelationInfoList;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGAImageView;

/**
 * 关注人列表、粉丝列表适配器
 * Created by xiaoy on 16/9/17.
 */
public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.FriendsListViewHolder> {

    List<RelationInfoList.UsersBean> mUsers;

    public RelationAdapter(List<RelationInfoList.UsersBean> list) {
        mUsers = list;
    }

    @Override
    public FriendsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(BaseApplication.context())
                .inflate(R.layout.my_relation_item, parent, false);

        return new FriendsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsListViewHolder holder, int position) {

        RelationInfoList.UsersBean usersBean = mUsers.get(position);

        LoadImage.getInstance().loadImageAsBitmap(usersBean.getAvatar_large(), holder.mIvIcon);
        holder.mTvName.setText(usersBean.getScreen_name());
        holder.mTvDescribe.setText(usersBean.getDescription());
        if (usersBean.isFollowing()) {
            holder.mIvFollow.setImageResource(R.drawable.card_icon_addtogroup_added);
        } else {
            holder.mIvFollow.setImageResource(R.drawable.card_icon_addtogroup);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class FriendsListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        BGAImageView mIvIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_describe)
        TextView mTvDescribe;
        @BindView(R.id.iv_follow)
        ImageView mIvFollow;

        public FriendsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
