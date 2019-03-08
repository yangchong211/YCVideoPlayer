package org.yczbj.ycvideoplayerlib.manager;


import org.yczbj.ycvideoplayerlib.player.VideoPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 视频播放器管理器
 *     revise: 将类置成final
 * </pre>
 */
public final class VideoPlayerManager {

    private VideoPlayer mVideoPlayer;
    private static volatile VideoPlayerManager sInstance;
    private VideoPlayerManager() {}

    /**
     * 一定要使用单例模式，保证同一时刻只有一个视频在播放，其他的都是初始状态
     * 单例模式
     * @return          VideoPlayerManager对象
     */
    public static VideoPlayerManager instance() {
        if (sInstance == null) {
            synchronized (VideoPlayerManager.class){
                if (sInstance == null){
                    sInstance = new VideoPlayerManager();
                }
            }
        }
        return sInstance;
    }


    /**
     * 获取对象
     * @return          VideoPlayerManager对象
     */
    public VideoPlayer getCurrentVideoPlayer() {
        return mVideoPlayer;
    }


    /**
     * 设置VideoPlayer
     * @param videoPlayer       VideoPlayerManager对象
     */
    public void setCurrentVideoPlayer(VideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }


    /**
     * 当视频正在播放或者正在缓冲时，调用该方法暂停视频
     */
    public void suspendVideoPlayer() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()){
                mVideoPlayer.pause();
            }
        }
    }


    /**
     * 当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放
     */
    public void resumeVideoPlayer() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()){
                mVideoPlayer.restart();
            }
        }
    }


    /**
     * 释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
     */
    public void releaseVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }


    /**
     * 处理返回键逻辑
     * 如果是全屏，则退出全屏
     * 如果是小窗口，则退出小窗口
     */
    public boolean onBackPressed() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }


}
