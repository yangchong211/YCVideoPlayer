package org.yczbj.ycvideoplayer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.yczbj.ycvideoplayer.ConstantVideo;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.player.video.VideoPlayer;
import org.yczbj.ycvideoplayerlib.ui.view.BasisVideoController;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoPlayer mVideoPlayer;
    private Button mBtnScaleNormal;
    private Button mBtnScale169;
    private Button mBtnScale43;
    private Button mBtnCrop;
    private Button mBtnGif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_video);
        initFindViewById();
        initVideoPlayer();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlayer != null) {
            mVideoPlayer.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlayer != null) {
            mVideoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        if (mVideoPlayer == null || !mVideoPlayer.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void initFindViewById() {
        mVideoPlayer = findViewById(R.id.video_player);
        mBtnScaleNormal = findViewById(R.id.btn_scale_normal);
        mBtnScale169 = findViewById(R.id.btn_scale_169);
        mBtnScale43 = findViewById(R.id.btn_scale_43);
        mBtnCrop = findViewById(R.id.btn_crop);
        mBtnGif = findViewById(R.id.btn_gif);
    }

    private void initVideoPlayer() {
        BasisVideoController controller = new BasisVideoController(this);
        //设置视频背景图
        Glide.with(this).load(R.drawable.image_default).into(controller.getThumb());
        //设置控制器
        mVideoPlayer.setVideoController(controller);
        mVideoPlayer.setUrl(ConstantVideo.VideoPlayerList[0]);
        mVideoPlayer.start();
    }

    private void initListener() {
        mBtnScaleNormal.setOnClickListener(this);
        mBtnScale169.setOnClickListener(this);
        mBtnScale43.setOnClickListener(this);
        mBtnCrop.setOnClickListener(this);
        mBtnGif.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mBtnScale169){
            mVideoPlayer.setScreenScaleType(ConstantKeys.PlayerScreenScaleType.SCREEN_SCALE_16_9);
        } else if (v == mBtnScaleNormal){
            mVideoPlayer.setScreenScaleType(ConstantKeys.PlayerScreenScaleType.SCREEN_SCALE_DEFAULT);
        }else if (v == mBtnScale43){
            mVideoPlayer.setScreenScaleType(ConstantKeys.PlayerScreenScaleType.SCREEN_SCALE_4_3);
        } else if (v == mBtnCrop){

        } else if (v == mBtnGif){

        }
    }
}
