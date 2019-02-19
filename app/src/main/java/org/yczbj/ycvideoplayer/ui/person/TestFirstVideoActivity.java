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
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import butterknife.Bind;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import cn.ycbjie.ycstatusbarlib.dlBar.StatusBarView;


/**
 * @author yc
 */
public class TestFirstVideoActivity extends BaseActivity  {


    @Bind(R.id.nice_video_player)
    VideoPlayer videoPlayer;


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
        return R.layout.activity_test_video_first;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);

        //必须关键的4步，播放视频最简单的方式
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        videoPlayer.setUp(ConstantVideo.VideoPlayerList[0], null);
        VideoPlayerController controller = new VideoPlayerController(this);
        videoPlayer.setController(controller);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
