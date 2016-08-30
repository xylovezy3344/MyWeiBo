package com.xiaoyu.myweibo.home;

import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.bean.WeiBoDetailList;
import com.xiaoyu.myweibo.utils.LoadImage;

import java.util.HashMap;
import java.util.List;

/**
 * 主页面微博列表适配器
 * Created by xiaoyu on 16-8-30.
 */
public class WeiBoListAdapter extends RecyclerView.Adapter<WeiBoListAdapter.MyViewHolder> {

    private static final String TAG = "WeiBoListAdapter：";

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
        holder.tvCreatedAt.setText(formatTime(mWeiBoDetailList.get(position).getCreated_at()));
        holder.tvWeiBoText.setText(mWeiBoDetailList.get(position).getText());
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

        public MyViewHolder(View view)
        {
            super(view);
            ivUserIcon = (ImageView) view.findViewById(R.id.iv_user_icon);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
            tvWeiBoText = (TextView) view.findViewById(R.id.tv_weibo_text);
        }
    }

    private String formatTime(String createdAt) {

        String[] strings = createdAt.split(" ");

        //微博生成时间
        int createdAtYear = Integer.valueOf(strings[5]);
        int createdAtDay = Integer.valueOf(strings[2]);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("Jan", 1);
        map.put("Feb", 2);
        map.put("Mar", 3);
        map.put("Apr", 4);
        map.put("May", 5);
        map.put("Jun", 6);
        map.put("Jul", 7);
        map.put("Aug", 8);
        map.put("Sep", 9);
        map.put("Oct", 10);
        map.put("Nov", 11);
        map.put("Dec", 12);

        int createdAtMonth = map.get(strings[1]);

        String createdAtTime = strings[3];
        String[] times = createdAtTime.split(":");
        int createdAtHour = Integer.valueOf(times[0]);
        int createdAtMinute = Integer.valueOf(times[1]);
        int createdAtSecond = Integer.valueOf(times[2]);

        //当前时间
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;

        // 年-月-日
        if (createdAtYear != year) {
            return createdAtYear + "年" + createdAtMonth + "月" + createdAtDay + "日";
        }
        // 月-日
        else if (createdAtMonth != month) {
            return createdAtMonth + "月" + createdAtDay + "日";
        }
        // 日
        else if (createdAtDay != day) {
            return createdAtMonth + "月" + createdAtDay + "日";
        }
        // x小时前
        else if (createdAtHour != hour) {
            return (hour - createdAtHour) + "小时前";
        }
        // x分钟前
        else if (createdAtMinute != minute) {
            return (minute - createdAtMinute) + "分钟前";
        }
        // 刚刚
        else {
            return "刚刚";
        }
    }
}
