package org.yczbj.ycvideoplayer.ui.test.view.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import java.io.IOException;
import java.util.LinkedHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.CustomMediaPlayer.CustomMediaPlayerAssertFolder;
import cn.jzvd.CustomMediaPlayer.JZMediaIjkplayer;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestApiSixActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @Bind(R.id.change_to_ijkplayer)
    Button changeToIjkplayer;
    @Bind(R.id.change_to_system_mediaplayer)
    Button changeToSystemMediaplayer;

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        //进入此页面修改MediaInterface，让此页面的jzvd正常工作
        JZVideoPlayer.setMediaInterface(new CustomMediaPlayerAssertFolder());
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_api_six;
    }

    @Override
    public void initView() {
        LinkedHashMap map = new LinkedHashMap();
        try {
            map.put(JZVideoPlayer.URL_KEY_DEFAULT, getAssets().openFd("local_video.mp4"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[] dataSourceObjects = new Object[2];
        dataSourceObjects[0] = map;
        dataSourceObjects[1] = this;
        videoplayer.setUp(dataSourceObjects, 0, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(videoplayer.thumbImageView);
    }

    @Override
    public void initListener() {
        changeToIjkplayer.setOnClickListener(this);
        changeToSystemMediaplayer.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_to_ijkplayer:
                JZVideoPlayer.setMediaInterface(new JZMediaIjkplayer());
                break;
            case R.id.change_to_system_mediaplayer:
                JZVideoPlayer.setMediaInterface(new JZMediaSystem());
                break;
            default:
                break;
        }
    }


}
