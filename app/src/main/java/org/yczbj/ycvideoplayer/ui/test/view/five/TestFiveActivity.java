package org.yczbj.ycvideoplayer.ui.test.view.five;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class TestFiveActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.pb)
    ProgressBar pb;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getContentView() {
        return R.layout.base_web_view;
    }

    
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JZCallBack(), "jzvd");
        webView.loadUrl("file:///android_asset/jzvd.html");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    public class JZCallBack {
        @JavascriptInterface
        public void adViewJiaoZiVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        JZVideoPlayerStandard webVieo = new JZVideoPlayerStandard(TestFiveActivity.this);
                        webVieo.setUp(VideoConstant.videoUrlList[1],
                                JZVideoPlayer.SCREEN_WINDOW_LIST, "饺子骑大马");
                        Picasso.with(TestFiveActivity.this)
                                .load(VideoConstant.videoThumbList[1])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JZUtils.dip2px(TestFiveActivity.this, top);
                        layoutParams.x = JZUtils.dip2px(TestFiveActivity.this, left);
                        layoutParams.height = JZUtils.dip2px(TestFiveActivity.this, height);
                        layoutParams.width = JZUtils.dip2px(TestFiveActivity.this, width);
                        webView.addView(webVieo, layoutParams);
                    } else {
                        JZVideoPlayerStandard webVieo = new JZVideoPlayerStandard(TestFiveActivity.this);
                        webVieo.setUp(VideoConstant.videoUrlList[2],
                                JZVideoPlayer.SCREEN_WINDOW_LIST, "饺子失态了");
                        Picasso.with(TestFiveActivity.this)
                                .load(VideoConstant.videoThumbList[2])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JZUtils.dip2px(TestFiveActivity.this, top);
                        layoutParams.x = JZUtils.dip2px(TestFiveActivity.this, left);
                        layoutParams.height = JZUtils.dip2px(TestFiveActivity.this, height);
                        layoutParams.width = JZUtils.dip2px(TestFiveActivity.this, width);
                        webView.addView(webVieo, layoutParams);
                    }
                }
            });
        }
    }


}
