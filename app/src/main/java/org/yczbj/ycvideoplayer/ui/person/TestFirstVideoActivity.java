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
    private VideoPlayerController controller;


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
        controller = new VideoPlayerController(this);
        videoPlayer.setController(controller);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void test(){
        //关于视频播放相关api
        //获取缓冲区百分比
        int bufferPercentage = videoPlayer.getBufferPercentage();
        //获取播放位置
        long currentPosition = videoPlayer.getCurrentPosition();
        //获取当前播放模式
        int currentState = videoPlayer.getCurrentState();
        //获取持续时长
        long duration = videoPlayer.getDuration();
        //获取最大音量
        int maxVolume = videoPlayer.getMaxVolume();
        //获取当前播放状态
        int playType = videoPlayer.getPlayType();
        //获取播放速度
        long tcpSpeed = videoPlayer.getTcpSpeed();
        //获取音量值
        int volume = videoPlayer.getVolume();


        //判断是否是否缓冲暂停
        boolean bufferingPaused = videoPlayer.isBufferingPaused();
        //判断视频是否正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
        boolean bufferingPlaying = videoPlayer.isBufferingPlaying();
        //判断视频是否播放完成
        boolean completed = videoPlayer.isCompleted();
        //判断视频是否播放错误
        boolean error = videoPlayer.isError();
        //判断视频是否播放全屏
        boolean fullScreen = videoPlayer.isFullScreen();
        //判断是否开始播放
        boolean idle = videoPlayer.isIdle();
        //判断视频是否正常播放
        boolean normal = videoPlayer.isNormal();
        //判断视频是否暂停播放
        boolean paused = videoPlayer.isPaused();
        //判断视频是否正在播放
        boolean playing = videoPlayer.isPlaying();
        //判断视频是否准备就绪
        boolean prepared = videoPlayer.isPrepared();
        //判断视频是否播放准备中
        boolean preparing = videoPlayer.isPreparing();
        //判断视频是否播放小窗口
        boolean tinyWindow = videoPlayer.isTinyWindow();

        //进入全屏模式
        videoPlayer.enterFullScreen();
        //进入竖屏的全屏模式
        videoPlayer.enterVerticalScreenScreen();
        //进入小窗口播放
        //注意：小窗口播放视频比例是        16：9
        videoPlayer.enterTinyWindow();

        //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
        videoPlayer.release();
        //释放播放器，注意一定要判断对象是否为空，增强严谨性
        videoPlayer.releasePlayer();

        //设置播放器类型，必须设置
        //输入值：ConstantKeys.IjkPlayerType.TYPE_IJK   或者  ConstantKeys.IjkPlayerType.TYPE_NATIVE
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
        //设置播放位置
        videoPlayer.seekTo(100);
        //设置播放速度，不必须
        videoPlayer.setSpeed(100);
        //设置视频链接
        videoPlayer.setUp("",null);
        //设置音量
        videoPlayer.setVolume(50);

        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);

        //开始播放
        videoPlayer.start();
        //暂停播放
        videoPlayer.pause();
        //开始播放
        videoPlayer.start(100);
        //重新播放
        videoPlayer.restart();
    }

}
