package com.xiaoyu.myweibo.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.myweibo.activity.SingleWeiboActivity;
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
     *
     * @param text
     * @return
     */
    private SpannableStringBuilder changeText(String text) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        //@用户文字变蓝
        //把文字中标点换成空格，方便后面查找位置
        String replacedText = text.replaceAll("[,.:，。：]", " ");

        int startIndex = replacedText.indexOf("@");
        int endIndex;

        while (startIndex != -1) {

            // 找到一个@的情况下，后面一定有冒号或者空格，或者@用户在结尾
            // 由于前面将标点换成空格，只需要找空格位置
            endIndex = replacedText.indexOf(" ", startIndex);

            if (endIndex == -1) {
                endIndex = replacedText.length();
            }

            String userName = replacedText.substring(startIndex + 1, endIndex);

            spannable.setSpan(new TextClick(userName, "user_name"), startIndex, endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            startIndex = replacedText.indexOf("@", endIndex);
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

        //全文两个字变蓝，后面的地址隐藏
        int fullTextStartIndex = text.indexOf("全文： http://m.weibo.cn/");
        int fullTextEndIndex = fullTextStartIndex + 2;

        if (fullTextStartIndex != -1) {

            String fullTextUrl = text.substring(fullTextStartIndex + 4, text.length());

            spannable.setSpan(new TextClick(fullTextUrl, "full_text"), fullTextStartIndex,
                    fullTextEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannable.delete(fullTextStartIndex + 2, spannable.length());

        }

        //微博短链接变蓝
        //找到开头的情况下，后面一定有空格或者是结尾（结尾是文字末尾，或者后面接“...全文”）
        String newText = text.replaceAll("[\\n\\r]", " ");
        newText = newText.replace("...", "   ");

        int urlStartIndex = newText.indexOf("http://t.cn/");
        int urlEndIndex;

        while (urlStartIndex != -1) {

            urlEndIndex = newText.indexOf(" ", urlStartIndex);

            if (urlEndIndex == -1) {
                urlEndIndex = spannable.length();
            }

            String shortUrl = newText.substring(urlStartIndex, urlEndIndex);

            spannable.replace(urlStartIndex, urlEndIndex, "查看链接");

            newText = newText.replaceFirst(shortUrl, "查看链接");

            spannable.setSpan(new TextClick(shortUrl, "short_url"), urlStartIndex,
                    urlStartIndex + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            urlStartIndex = newText.indexOf("http://t.cn/", urlStartIndex + 5);
        }

        return spannable;
    }

    private class TextClick extends ClickableSpan {

        private String text;
        private String tag;

        TextClick(String text, String tag) {
            super();
            this.text = text;
            this.tag = tag;
        }

        @Override
        public void onClick(View view) {

            if ("user_name".equals(tag)) {
                //用户名字，跳主页
                Intent intent = new Intent(BaseApplication.context(), UserHomeActivity.class);
                intent.putExtra(UserHomeActivity.USER_NAME, text);
                AppManager.getAppManager().currentActivity().startActivity(intent);
            } else if ("topic".equals(tag)) {
                //话题，跳话题页面
            } else if ("short_url".equals(tag)) {
                //微博短链接，跳浏览器打开
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(text);
                intent.setData(content_url);
                AppManager.getAppManager().currentActivity().startActivity(intent);
            } else {
                //全文，跳单条微博页面，带微博url地址
                LinearLayout parent = (LinearLayout) getParent();
                parent.callOnClick();
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }
    }
}
