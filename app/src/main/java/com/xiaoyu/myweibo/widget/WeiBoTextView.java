package com.xiaoyu.myweibo.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.myweibo.base.BaseApplication;

/**
 * @XX 变蓝色， 话题边蓝色以及设置点击事件 的自定义TextVIew
 * Created by xiaoyu on 16-9-6.
 */
public class WeiBoTextView extends TextView {


    public WeiBoTextView(Context context) {
        super(context);
    }

    public WeiBoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiBoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeiBoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

            int space = text.indexOf(" ", startIndex);
            int colon = text.indexOf(":", startIndex);
//            int colon1 = text.indexOf(":", startIndex);
//            int colon2 = text.indexOf("：", startIndex);
//            if (colon1 != -1 && colon2 != -1) {
//                colon = colon1 < colon2 ? colon1 : colon2;
//            } else if (colon1 == -1 && colon2 == -1) {
//                colon = -1;
//            } else  {
//                colon = colon1 != -1 ? colon1 : colon2;
//            }

            if (space != -1 && colon != -1) {
                endIndex = space < colon ? space : colon;
            } else if (space == -1 && colon == -1) {
                endIndex = text.length();
            } else  {
                endIndex = space != -1 ? space : colon;
            }

            String userName = text.substring(startIndex + 1, endIndex);

            spannable.setSpan(new TextClick(userName), startIndex, endIndex,
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

                spannable.setSpan(new TextClick(topic), topicStartIndex,
                        topicEndIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                topicStartIndex = text.indexOf("#", topicEndIndex + 1);
            }
        }

        return spannable;
    }

    private class TextClick extends ClickableSpan {

        private String text;

        public TextClick(String text) {
            super();
            this.text = text;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(BaseApplication.context(), text, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }
    }
}
