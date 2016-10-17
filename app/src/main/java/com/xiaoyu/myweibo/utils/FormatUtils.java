package com.xiaoyu.myweibo.utils;

import android.text.format.Time;

import java.util.HashMap;

/**
 * 格式化
 * Created by xiaoyu on 16-8-31.
 */
public class FormatUtils {

    public static String formatTime(String createdAt) {

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

    public static String createdAt(String createdAt) {

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

        return createdAtMonth + "-" + createdAtDay + " " + createdAtHour + ":" + createdAtMinute;

    }
}
