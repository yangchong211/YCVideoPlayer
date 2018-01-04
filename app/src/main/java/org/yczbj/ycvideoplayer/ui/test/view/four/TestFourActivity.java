package org.yczbj.ycvideoplayer.ui.test.view.four;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class TestFourActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    @Bind(R.id.btn_fullscreen)
    Button fullscreen;
    @Bind(R.id.btn_tiny_window)
    Button tinyWindow;

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_four;
    }


    @Override
    public void initView() {
        jzVideo.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo.thumbImageView);
    }

    @Override
    public void initListener() {
        fullscreen.setOnClickListener(this);
        tinyWindow.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fullscreen:
                JZVideoPlayerStandard.startFullscreen(this,
                        JZVideoPlayerStandard.class, VideoConstant.videoUrlList[6], "饺子辛苦了");
                break;
            case R.id.btn_tiny_window:

                break;
            default:
                break;
        }
    }

}
