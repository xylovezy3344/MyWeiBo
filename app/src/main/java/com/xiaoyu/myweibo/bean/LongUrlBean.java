package com.xiaoyu.myweibo.bean;

import java.util.List;

/**
 *
 * Created by xiaoyu on 16-9-11.
 */
public class LongUrlBean {
    /**
     * result : true
     * url_short : http://t.cn/RcVj5KF
     * url_long : http://www.miaopai.com/show/iSNM67wlBY0XkMS8cOXSmA__.htm
     * type : 39
     * transcode : 0
     */

    private List<UrlsBean> urls;

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    public static class UrlsBean {
        private boolean result;
        private String url_short;
        private String url_long;
        private int type;
        private int transcode;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getUrl_short() {
            return url_short;
        }

        public void setUrl_short(String url_short) {
            this.url_short = url_short;
        }

        public String getUrl_long() {
            return url_long;
        }

        public void setUrl_long(String url_long) {
            this.url_long = url_long;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTranscode() {
            return transcode;
        }

        public void setTranscode(int transcode) {
            this.transcode = transcode;
        }
    }
}
