package org.yczbj.ycvideoplayer.ui.person;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoControlListener;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import butterknife.BindView;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


/**
 * @author yc
 */
public class TestSecondFullActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.nice_video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.btn_tiny_1)
    Button btnTiny1;
    @BindView(R.id.btn_tiny_2)
    Button btnTiny2;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().suspendVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()){
            return;
        }else {
            VideoPlayerManager.instance().releaseVideoPlayer();
        }
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        VideoPlayerManager.instance().resumeVideoPlayer();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_video_second;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);

        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        videoPlayer.setUp(ConstantVideo.VideoPlayerList[0], null);
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLength(98000);
        Glide.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(controller.imageView());
        //设置中间播放按钮是否显示
        controller.setCenterPlayer(true,R.drawable.ic_player_center_start);
        controller.setTopPadding(24.0f);
        videoPlayer.setController(controller);
    }

    @Override
    public void initListener() {
        btnTiny1.setOnClickListener(this);
        btnTiny2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tiny_1:
                if (videoPlayer.isIdle()) {
                    videoPlayer.start();
                }
                videoPlayer.enterVerticalScreenScreen();
                break;
            case R.id.btn_tiny_2:
                if (videoPlayer.isIdle()) {
                    videoPlayer.start();
                }
                videoPlayer.enterFullScreen();
                break;
            default:
                break;
        }
    }
}
