package org.yczbj.ycvideoplayer;

import android.widget.ImageView;

import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.view.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.inter.dev.OnVideoControlListener2;
import org.yczbj.ycvideoplayerlib.tool.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.view.player.VideoPlayer;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


/**
 * @author yc
 */
public class TestNormalActivity extends BaseActivity  {


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
        return R.layout.activity_full_video2;
    }

    @Override
    public void initView() {
        StateAppBar.translucentStatusBar(this, true);
        videoPlayer = (VideoPlayer) findViewById(R.id.video_player);
        //必须关键的4步，播放视频最简单的方式
        videoPlayer.setPlayerType(ConstantKeys.VideoPlayerType.TYPE_IJK);
        videoPlayer.setUp(ConstantVideo.VideoPlayerList[0], null);
        controller = new VideoPlayerController(this);
        controller.setTopPadding(24.0f);
        videoPlayer.continueFromLastPosition(false);
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
        videoPlayer.setPlayerType(ConstantKeys.VideoPlayerType.TYPE_IJK);
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

        //设置是否显示视频头部的下载，分享，其他等控件是否显示
        controller.setTopVisibility(true);
        controller.setTop(20);
        //设置top到顶部的距离
        controller.setTopPadding(30);
        //设置加载loading类型
        controller.setLoadingType(ConstantKeys.Loading.LOADING_RING);
        //设置不操作后，多久自动隐藏头部和底部布局
        controller.setHideTime(8000);
        //获取ImageView的对象
        ImageView imageView = controller.imageView();
        //重新设置
        controller.reset();
        //设置图片
        //controller.setImage(R.drawable.ic_back_right);
        //设置视频时长
        controller.setLength(1000);
        //设置视频标题
        controller.setTitle("小杨逗比");
        boolean lock = controller.getLock();
        //设置横屏播放时，tv和audio图标是否显示
        controller.setTvAndAudioVisibility(true,true);

        //设置视频分享，下载，音视频转化点击事件
        controller.setOnVideoControlListener(new OnVideoControlListener2() {
            @Override
            public void onVideoControlClick(int type) {

            }
        });

        //VideoPlayerManager对象
        VideoPlayerManager instance = VideoPlayerManager.instance();
        //当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放
        instance.resumeVideoPlayer();
        //当视频正在播放或者正在缓冲时，调用该方法暂停视频
        instance.suspendVideoPlayer();
        //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
        instance.releaseVideoPlayer();
        //处理返回键逻辑
        //如果是全屏，则退出全屏
        //如果是小窗口，则退出小窗口
        instance.onBackPressed();
        //获取对象
        VideoPlayer currentVideoPlayer = instance.getCurrentVideoPlayer();
        //设置VideoPlayer
        instance.setCurrentVideoPlayer(videoPlayer);
    }

}
