package org.yczbj.ycvideoplayer.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.yczbj.ycvideoplayer.ConstantVideo;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayerlib.kernel.view.VideoView;
import org.yczbj.ycvideoplayerlib.ui.view.BasisVideoController;

import java.util.ArrayList;
import java.util.List;


public class MultipleActivity extends AppCompatActivity {

    private static final String VOD_URL_1 = ConstantVideo.VideoPlayerList[3];
    private static final String VOD_URL_2 = ConstantVideo.VideoPlayerList[0];
    private VideoView player1;
    private VideoView player2;
    private List<VideoView> mVideoViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_play);

        initFindViewById();
        initVideoPlayer();
    }

    private void initFindViewById() {
        player1 = findViewById(R.id.video_player1);
        player2 = findViewById(R.id.video_player2);
    }

    private void initVideoPlayer() {
        player1.setUrl(VOD_URL_1);
        //必须设置
        player1.setEnableAudioFocus(false);
        BasisVideoController controller1 = new BasisVideoController(this);
        player1.setVideoController(controller1);
        mVideoViews.add(player1);

        player2.setUrl(VOD_URL_2);
        //必须设置
        player2.setEnableAudioFocus(false);
        BasisVideoController controller2 = new BasisVideoController(this);
        player2.setVideoController(controller2);
        mVideoViews.add(player2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (VideoView vv : mVideoViews) {
            vv.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (VideoView vv : mVideoViews) {
            vv.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (VideoView vv : mVideoViews) {
            vv.release();
        }
    }

    @Override
    public void onBackPressed() {
        for (VideoView vv : mVideoViews) {
            if (vv.onBackPressed())
                return;
        }
        super.onBackPressed();
    }
}
