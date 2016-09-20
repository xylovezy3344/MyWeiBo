package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.bean.LongUrlBean;
import com.xiaoyu.myweibo.network.ConvertUrl;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class WebContentActivity extends AppCompatActivity {

    public static final String WEB_CONTENT = "WEB_CONTENT";
    @BindView(R.id.wv_web_content)
    WebView mWvWebContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_content_act);
        ButterKnife.bind(this);

        String shortUrl = getIntent().getStringExtra(WEB_CONTENT);

        ConvertUrl.getLongUrl(shortUrl, new Observer<LongUrlBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LongUrlBean longUrlBean) {
                String longUrl = longUrlBean.getUrls().get(0).getUrl_long();
                mWvWebContent.loadUrl(longUrl);
            }
        });
    }
}
