package org.yczbj.ycvideoplayer.ui.person;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;

import butterknife.BindView;


/**
 * @author yc
 */
public class TestThirdVideoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.nice_video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.btn_tiny_1)
    Button btnTiny1;
    @BindView(R.id.btn_tiny_2)
    Button btnTiny2;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_video_third;
    }

    @Override
    public void initView() {
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        videoPlayer.setUp(ConstantVideo.VideoPlayerList[0], null);
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setLoadingType(ConstantKeys.Loading.LOADING_RING);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLength(98000);
        Glide.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(controller.imageView());
        controller.setHideTime(2000);
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
                    Toast.makeText(this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
                } else {
                    videoPlayer.enterTinyWindow();
                }
                break;
            case R.id.btn_tiny_2:
                if (videoPlayer.isIdle()) {
                    videoPlayer.start();
                }
                videoPlayer.enterVerticalScreenScreen();
                break;
            default:
                break;
        }
    }
}
