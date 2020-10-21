/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.yc.video.old.other;


import com.yc.video.old.player.OldVideoPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 视频播放器管理器
 *     revise: 将类置成final
 * </pre>
 */
@Deprecated
public final class VideoPlayerManager {

    private OldVideoPlayer mVideoPlayer;
    /**
     * 用volatile关键字修饰对象
     * 为何要确保只有一个对象，为了保证任何时候有且只有一个视频播放，尤其是在列表中播放
     */
    private static volatile VideoPlayerManager sInstance;

    /**
     * 构造方法，避免直接new
     */
    private VideoPlayerManager() {
        //避免初始化
    }

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
    public OldVideoPlayer getCurrentVideoPlayer() {
        return mVideoPlayer;
    }


    /**
     * 设置VideoPlayer
     * @param videoPlayer       VideoPlayerManager对象
     */
    public void setCurrentVideoPlayer(OldVideoPlayer videoPlayer) {
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
            //如果是全屏幕，则退出全屏
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                //如果是小窗口播放，则退出小窗口
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }


}
