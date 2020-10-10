package org.yczbj.ycvideoplayerlib.player.inter;

import android.content.res.AssetFileDescriptor;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.Map;

/**
 * 抽象的播放器，继承此接口扩展自己的播放器
 */
public abstract class AbstractPlayer {

    /**
     * 开始渲染视频画面
     */
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;

    /**
     * 缓冲开始
     */
    public static final int MEDIA_INFO_BUFFERING_START = 701;

    /**
     * 缓冲结束
     */
    public static final int MEDIA_INFO_BUFFERING_END = 702;

    /**
     * 视频旋转信息
     */
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;

    /**
     * 播放器事件回调
     */
    protected PlayerEventListener mPlayerEventListener;

    /**
     * 初始化播放器实例
     * 视频播放器第一步：创建视频播放器
     */
    public abstract void initPlayer();

    /**
     * 设置播放地址
     * 视频播放器第二步：设置数据
     * @param path    播放地址
     * @param headers 播放地址请求头
     */
    public abstract void setDataSource(String path, Map<String, String> headers);

    /**
     * 用于播放raw和asset里面的视频文件
     */
    public abstract void setDataSource(AssetFileDescriptor fd);

    /**
     * 设置渲染视频的View,主要用于TextureView
     * 视频播放器第三步：设置surface
     * @param surface                           surface
     */
    public abstract void setSurface(Surface surface);

    /**
     * 准备开始播放（异步）
     * 视频播放器第四步：开始加载【异步】
     */
    public abstract void prepareAsync();

    /**
     * 播放
     */
    public abstract void start();

    /**
     * 暂停
     */
    public abstract void pause();

    /**
     * 停止
     */
    public abstract void stop();

    /**
     * 重置播放器
     */
    public abstract void reset();

    /**
     * 是否正在播放
     * @return                                  是否正在播放
     */
    public abstract boolean isPlaying();

    /**
     * 调整进度
     */
    public abstract void seekTo(long time);

    /**
     * 释放播放器
     */
    public abstract void release();

    /**
     * 获取当前播放的位置
     * @return                                  获取当前播放的位置
     */
    public abstract long getCurrentPosition();

    /**
     * 获取视频总时长
     * @return                                  获取视频总时长
     */
    public abstract long getDuration();

    /**
     * 获取缓冲百分比
     * @return                                  获取缓冲百分比
     */
    public abstract int getBufferedPercentage();

    /**
     * 设置渲染视频的View,主要用于SurfaceView
     * @param holder                            holder
     */
    public abstract void setDisplay(SurfaceHolder holder);

    /**
     * 设置音量
     * @param v1                                v1
     * @param v2                                v2
     */
    public abstract void setVolume(float v1, float v2);

    /**
     * 设置是否循环播放
     * @param isLooping                         布尔值
     */
    public abstract void setLooping(boolean isLooping);

    /**
     * 设置其他播放配置
     */
    public abstract void setOptions();

    /**
     * 设置播放速度
     * @param speed                             速度
     */
    public abstract void setSpeed(float speed);

    /**
     * 获取播放速度
     * @return                                  播放速度
     */
    public abstract float getSpeed();

    /**
     * 获取当前缓冲的网速
     * @return                                  获取网络
     */
    public abstract long getTcpSpeed();

    /**
     * 绑定VideoView
     */
    public void setPlayerEventListener(PlayerEventListener playerEventListener) {
        this.mPlayerEventListener = playerEventListener;
    }

    public interface PlayerEventListener {

        /**
         * 异常
         */
        void onError();

        /**
         * 完成
         */
        void onCompletion();

        /**
         * 视频信息
         * @param what                          what
         * @param extra                         extra
         */
        void onInfo(int what, int extra);

        /**
         * 准备
         */
        void onPrepared();

        /**
         * 视频size变化监听
         */
        void onVideoSizeChanged(int width, int height);

    }

}

