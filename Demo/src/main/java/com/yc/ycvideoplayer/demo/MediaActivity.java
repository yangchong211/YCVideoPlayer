package com.yc.ycvideoplayer.demo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.ycvideoplayer.ConstantVideo;

import org.yc.ycvideoplayer.R;
import com.yc.video.tool.BaseToast;

public class MediaActivity extends AppCompatActivity {

    private VideoView mVideo;
    private Button mBtnStart;
    private Button mBtnPause;
    private MediaController mMediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        initView();
        initListener();
    }

    private void initView() {
        mVideo = findViewById(R.id.video);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnPause = findViewById(R.id.btn_pause);

        Uri uri = Uri.parse(ConstantVideo.VideoPlayerList[2]);
        mVideo.setVideoURI(uri);
        mMediaController = new MediaController(this);
        mVideo.setMediaController(mMediaController);
        mVideo.start();
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //         mp.setLooping(true);
                mp.start();// 播放
                BaseToast.showRoundRectToast("开始播放！");
            }
        });
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                BaseToast.showRoundRectToast("播放完毕！");
            }
        });
    }

    private void initListener() {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideo.start();
            }
        });
        mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideo.pause();
            }
        });
    }

}
