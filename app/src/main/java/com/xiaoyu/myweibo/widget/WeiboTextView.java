package com.xiaoyu.myweibo.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.xiaoyu.myweibo.activity.SingleWeiboActivity;
import com.xiaoyu.myweibo.activity.WebContentActivity;
import com.xiaoyu.myweibo.base.BaseApplication;
import com.xiaoyu.myweibo.activity.TopicActivity;
import com.xiaoyu.myweibo.activity.UserHomeActivity;
import com.xiaoyu.myweibo.utils.AppManager;

/**
 * @XX 变蓝色， 话题边蓝色以及设置点击事件 的自定义TextVIew
 * Created by xiaoyu on 16-9-6.
 */
public class WeiboTextView extends TextView {


    public WeiboTextView(Context context) {
        super(context);
    }

    public WeiboTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiboTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeiboTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        this.setMovementMethod(LinkMovementMethod.getInstance());
        super.setText(changeText((String) text), type);
    }

    /**
     * 微博文字变颜色 （@XX 变蓝色， 话题边蓝色以及设置点击事件）
     * @param text
     * @return
     */
    private SpannableStringBuilder changeText(String text) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        //@用户文字变蓝
        int startIndex = text.indexOf("@");
        int endIndex;

        while (startIndex != -1) {

            // 找到一个@的情况下，后面一定有冒号或者空格，或者@用户在结尾
            int space = text.indexOf(" ", startIndex);
            int colonEn = text.indexOf(":", startIndex);
            int colonCn = text.indexOf("：", startIndex);

            if (space == -1 && colonEn == -1 &&  colonCn == -1) {
                endIndex = text.length();
            } else if (space == -1 && colonEn == -1) {
                endIndex = colonCn;
            } else if (space == -1 && colonCn == -1) {
                endIndex = colonEn;
            } else if (colonEn == -1 && colonCn == -1) {
                endIndex = space;
            } else if (space == -1) {
                endIndex = Math.min(colonEn, colonCn);
            } else if (colonEn == -1) {
                endIndex = Math.min(space, colonCn);
            } else if (colonCn == -1) {
                endIndex = Math.min(colonEn, space);
            } else {
                endIndex = Math.min(Math.min(space, colonEn), colonCn);
            }

            String userName = text.substring(startIndex + 1, endIndex);

            spannable.setSpan(new TextClick(userName, "user_name"), startIndex, endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            startIndex = text.indexOf("@", endIndex);
        }

        // #...# 微博话题变蓝
        int topicStartIndex = text.indexOf("#");
        int topicEndIndex;

        while (topicStartIndex != -1) {

            topicEndIndex = text.indexOf("#", topicStartIndex + 1);

            if (topicEndIndex != -1) {

                String topic = text.substring(topicStartIndex + 1, topicEndIndex);

                spannable.setSpan(new TextClick(topic, "topic"), topicStartIndex,
                        topicEndIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                topicStartIndex = text.indexOf("#", topicEndIndex + 1);
            }
        }

        //微博短链接变蓝
        int urlStartIndex = text.indexOf("http://t.cn/");
        int urlEndIndex;

        while (urlStartIndex != -1) {

            urlEndIndex = urlStartIndex + 19;

            if (urlEndIndex != -1) {

                String shortUrl = text.substring(urlStartIndex , urlEndIndex);

                spannable.setSpan(new TextClick(shortUrl, "short_url"), urlStartIndex,
                        urlEndIndex , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                urlStartIndex = text.indexOf("http://t.cn/", urlEndIndex + 1);
            }
        }

        //全文两个字变蓝，后面的地址隐藏
        int fullTextStartIndex = text.indexOf("全文： http://m.weibo.cn/");
        int fullTextEndIndex = fullTextStartIndex + 2;

        if (fullTextStartIndex != -1) {

            String fullTextUrl = text.substring(fullTextStartIndex + 4, text.length());

            spannable.setSpan(new TextClick(fullTextUrl, "full_text"), fullTextStartIndex,
                    fullTextEndIndex , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannable.delete(fullTextStartIndex + 2, text.length());

        }

        return spannable;
    }

    private class TextClick extends ClickableSpan {

        private String text;
        private String tag;

        public TextClick(String text, String tag) {
            super();
            this.text = text;
            this.tag = tag;
        }

        @Override
        public void onClick(View view) {

            Intent intent;

            if ("user_name".equals(tag)) {
                intent = new Intent(BaseApplication.context(), UserHomeActivity.class);
                intent.putExtra(UserHomeActivity.USER_UID, text);
            } else if ("topic".equals(tag)){
                intent = new Intent(BaseApplication.context(), TopicActivity.class);
                intent.putExtra(TopicActivity.TOPIC_TITLE, text);
            } else if ("short_url".equals(tag)){
                intent = new Intent(BaseApplication.context(), WebContentActivity.class);
                intent.putExtra(WebContentActivity.WEB_CONTENT, text);
            } else {
                intent = new Intent(BaseApplication.context(), SingleWeiboActivity.class);
                intent.putExtra(SingleWeiboActivity.SINGLE_WEIBO, text);
            }

            AppManager.getAppManager().currentActivity().startActivity(intent);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }

    }
}
