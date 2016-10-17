package com.xiaoyu.myweibo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaoyu.myweibo.R;
import com.xiaoyu.myweibo.adapter.CommentsAdapter;
import com.xiaoyu.myweibo.adapter.WeiboListAdapter;
import com.xiaoyu.myweibo.base.BaseActivity;
import com.xiaoyu.myweibo.bean.CommentList;
import com.xiaoyu.myweibo.bean.WeiboDetailList;
import com.xiaoyu.myweibo.contract.SingleWeiboContract;
import com.xiaoyu.myweibo.presenter.SingleWeiboPresenter;
import com.xiaoyu.myweibo.utils.AppManager;
import com.xiaoyu.myweibo.utils.FormatUtils;
import com.xiaoyu.myweibo.utils.LoadImageUtils;
import com.xiaoyu.myweibo.utils.PreviewPhoto;
import com.xiaoyu.myweibo.utils.ProgressDialogUtils;
import com.xiaoyu.myweibo.widget.WeiboTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * 单条微博详情页面
 * Created by xiaoyu on 16-9-11.
 */
public class SingleWeiboActivity extends BaseActivity implements BGANinePhotoLayout.Delegate,
        SingleWeiboContract.View {

    public static final String SINGLE_WEIBO = "SINGLE_WEIBO";
    public static final String WEIBO_STYLE = "WEIBO_STYLE";

    //表示首次请求数据
    public static int FIRST_GET = 0;
    //上拉加载更多
    public static int UP_REFRESH = 1;
    //下拉刷新
    public static int DOWN_REFRESH = 2;

    private SingleWeiboPresenter mPresenter;

    @BindView(R.id.ll_weibo)
    LinearLayout llWeibo;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;

    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_created_at)
    TextView tvCreatedAt;
    @BindView(R.id.tv_weibo_text_friend)
    WeiboTextView tvFriendWeiBoText;
    //九宫格显示缩略图
    @BindView(R.id.nine_pic_friend)
    BGANinePhotoLayout friendNinePic;

    @BindView(R.id.tv_weibo_text_source)
    WeiboTextView tvSourceWeiBoText;
    @BindView(R.id.ll_source_weibo)
    LinearLayout llSourceWeiBo;
    //九宫格显示缩略图
    @BindView(R.id.nine_pic_source)
    BGANinePhotoLayout sourceNinePic;

    //转发 评论 赞
    @BindView(R.id.ll_reposts)
    LinearLayout llReposts;
    @BindView(R.id.tv_reposts)
    TextView tvReposts;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;
    @BindView(R.id.tv_comments)
    TextView tvComment;
    @BindView(R.id.ll_attitudes)
    LinearLayout llAttitudes;
    @BindView(R.id.iv_attitudes)
    ImageView ivAttitudes;
    @BindView(R.id.tv_attitudes)
    TextView tvAttitudes;

    @BindView(R.id.rv_comment_list)
    RecyclerView mRvCommentList;

    private long mWeiboId;
    private CommentsAdapter mAdapter;
    private List<CommentList.CommentsBean> mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_weibo_act);

        initView();
        initData();

    }

    private void initView() {
        ButterKnife.bind(this);
        mPresenter = new SingleWeiboPresenter(this);
    }

    private void initData() {

        String weiboStyle = getIntent().getStringExtra(WEIBO_STYLE);
        String weiboDetail = getIntent().getStringExtra(SINGLE_WEIBO);

        Gson gson = new Gson();

        if (weiboStyle.equals("friend")) {
            WeiboDetailList.StatusesBean friendWeibo = gson.fromJson(weiboDetail,
                    WeiboDetailList.StatusesBean.class);
            mWeiboId = friendWeibo.getId();
            fillData(friendWeibo, friendWeibo.getRetweeted_status());
        } else if (weiboStyle.equals("source")) {
            WeiboDetailList.StatusesBean.RetweetedStatusBean sourceWeibo = gson.fromJson(weiboDetail,
                    WeiboDetailList.StatusesBean.RetweetedStatusBean.class);
            mWeiboId = sourceWeibo.getId();
            fillData(null, sourceWeibo);
        }

        mPresenter.getCommentList(FIRST_GET, mWeiboId, null);
    }

    private void fillData(WeiboDetailList.StatusesBean friendWeibo,
                          WeiboDetailList.StatusesBean.RetweetedStatusBean sourceWeibo) {

        if (friendWeibo != null) {
            /**
             * 转发微博者
             */
            //头像
            LoadImageUtils.getInstance().loadImageAsBitmap(friendWeibo
                    .getUser().getAvatar_hd(), ivUserIcon);
            //用户名
            tvUserName.setText(friendWeibo.getUser().getName());
            //发微博时间
            tvCreatedAt.setText(FormatUtils.formatTime(friendWeibo
                    .getCreated_at()));
            //微博文字内容
            String friendText = friendWeibo.getText();
            tvFriendWeiBoText.setText(friendText);
            //微博图片内容
            List<WeiboDetailList.StatusesBean.PicUrlsBean> PicUrlsBeansF = friendWeibo.getPic_urls();
            ArrayList<String> friendPicUrls = new ArrayList<>();
            for (WeiboDetailList.StatusesBean.PicUrlsBean bean : PicUrlsBeansF) {
                String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
                friendPicUrls.add(picUrl);
            }

            friendNinePic.init(AppManager.getAppManager().currentActivity());
            friendNinePic.setDelegate(this);
            friendNinePic.setData(friendPicUrls);
        } else {
            llWeibo.setVisibility(View.GONE);
        }

        /**
         * 原微博，被转发者
         */
        if (sourceWeibo != null) {

            //微博文字内容
            String sourceText;
            if (sourceWeibo.getUser() != null) {
                sourceText = "@" + sourceWeibo.getUser()
                        .getName() + ":" + sourceWeibo.getText();
            } else {
                sourceText = sourceWeibo.getText();
            }


            tvSourceWeiBoText.setText(sourceText);
            //微博图片内容
            List<WeiboDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean> PicUrlsBeansS =
                    sourceWeibo.getPic_urls();
            if (PicUrlsBeansS != null) {
                ArrayList<String> sourcePicUrls = new ArrayList<>();
                for (WeiboDetailList.StatusesBean.RetweetedStatusBean.PicUrlsBean bean : PicUrlsBeansS) {
                    String picUrl = bean.getThumbnail_pic().replace("thumbnail", "bmiddle");
                    sourcePicUrls.add(picUrl);
                }

                sourceNinePic.init(AppManager.getAppManager().currentActivity());
                sourceNinePic.setDelegate(this);
                sourceNinePic.setData(sourcePicUrls);
            }

        } else {
            llSourceWeiBo.setVisibility(View.GONE);
        }

        /**
         * 转发 评论 赞
         */
        int repostsCount, commentsCount, attitudesCount;

        if (friendWeibo != null) {
            repostsCount = friendWeibo.getReposts_count();
            commentsCount = friendWeibo.getComments_count();
            attitudesCount = friendWeibo.getAttitudes_count();
        } else {
            repostsCount = sourceWeibo.getReposts_count();
            commentsCount = sourceWeibo.getComments_count();
            attitudesCount = sourceWeibo.getAttitudes_count();
        }

        if (repostsCount == 0) {
            tvReposts.setText("转发");
        } else {
            tvReposts.setText(repostsCount + "");
        }
        if (commentsCount == 0) {
            tvComment.setText("评论");
        } else {
            tvComment.setText(commentsCount + "");
        }
        if (attitudesCount == 0) {
            tvAttitudes.setText("赞");
        } else {
            tvAttitudes.setText(attitudesCount + "");
        }
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

        if (ninePhotoLayout != null) {

            if (ninePhotoLayout.getItemCount() == 1) {
                // 预览单张图片
                PreviewPhoto.previewPhoto(ninePhotoLayout.getCurrentClickItem());
            } else if (ninePhotoLayout.getItemCount() > 1) {
                // 预览多张图片
                PreviewPhoto.previewPhotos(ninePhotoLayout.getData(), ninePhotoLayout.getCurrentClickItemPosition());
            }
        }
    }

    @Override
    public boolean onLongClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view,
                                            int position, String model, List<String> models) {
        return false;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogUtils.ShowProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        ProgressDialogUtils.hideProgressDialog();
    }

    @Override
    public void showComments(List<CommentList.CommentsBean> list) {
        mCommentList = list;
        //设置adapter
        mAdapter = new CommentsAdapter(mCommentList);
        mRvCommentList.setAdapter(mAdapter);
    }

    @Override
    public void refreshComments(List<CommentList.CommentsBean> list) {
        mCommentList = list;
        mAdapter.notifyDataSetChanged();
    }
}
