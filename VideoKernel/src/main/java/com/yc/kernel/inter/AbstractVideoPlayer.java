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
package com.yc.kernel.inter;

import android.content.res.AssetFileDescriptor;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.Map;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 抽象的播放器
 *     revise:
 * </pre>
 */
public abstract class AbstractVideoPlayer {

    /**
     * 播放器事件回调
     */
    protected VideoPlayerListener mPlayerEventListener;

    /*----------------------------第一部分：视频初始化实例对象方法----------------------------------*/
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

    /*----------------------------第二部分：视频播放器状态方法----------------------------------*/

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

    /*----------------------------第三部分：player绑定view后，需要监听播放状态--------------------*/

    /**
     * 绑定VideoView，监听播放异常，完成，开始准备，视频size变化，视频信息等操作
     */
    public void setPlayerEventListener(VideoPlayerListener playerEventListener) {
        this.mPlayerEventListener = playerEventListener;
    }

}

