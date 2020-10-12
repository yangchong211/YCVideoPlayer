package org.yczbj.ycvideoplayerlib.player;


import androidx.annotation.Nullable;

import com.yc.kernel.factory.PlayerFactory;
import com.yc.kernel.impl.media.MediaPlayerFactory;

import org.yczbj.ycvideoplayerlib.surface.SurfaceFactory;
import org.yczbj.ycvideoplayerlib.surface.TextureViewFactory;



/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 播放器全局配置
 *     revise:
 * </pre>
 */
public class VideoPlayerConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {

        /**
         * 默认是关闭日志的
         */
        private boolean mIsEnableLog = false;
        private boolean mPlayOnMobileNetwork;
        private boolean mEnableOrientation;
        private boolean mEnableAudioFocus = true;
        private ProgressManager mProgressManager;
        private PlayerFactory mPlayerFactory;
        private int mScreenScaleType;
        private SurfaceFactory mRenderViewFactory;
        private boolean mAdaptCutout = true;

        /**
         * 是否监听设备方向来切换全屏/半屏， 默认不开启
         */
        public Builder setEnableOrientation(boolean enableOrientation) {
            mEnableOrientation = enableOrientation;
            return this;
        }

        /**
         * 在移动环境下调用start()后是否继续播放，默认不继续播放
         */
        public Builder setPlayOnMobileNetwork(boolean playOnMobileNetwork) {
            mPlayOnMobileNetwork = playOnMobileNetwork;
            return this;
        }

        /**
         * 是否开启AudioFocus监听， 默认开启
         */
        public Builder setEnableAudioFocus(boolean enableAudioFocus) {
            mEnableAudioFocus = enableAudioFocus;
            return this;
        }

        /**
         * 设置进度管理器，用于保存播放进度
         */
        public Builder setProgressManager(@Nullable ProgressManager progressManager) {
            mProgressManager = progressManager;
            return this;
        }

        /**
         * 是否打印日志
         */
        public Builder setLogEnabled(boolean enableLog) {
            mIsEnableLog = enableLog;
            return this;
        }

        /**
         * 自定义播放核心
         */
        public Builder setPlayerFactory(PlayerFactory playerFactory) {
            mPlayerFactory = playerFactory;
            return this;
        }

        /**
         * 设置视频比例
         */
        public Builder setScreenScaleType(int screenScaleType) {
            mScreenScaleType = screenScaleType;
            return this;
        }

        /**
         * 自定义RenderView
         */
        public Builder setRenderViewFactory(SurfaceFactory renderViewFactory) {
            mRenderViewFactory = renderViewFactory;
            return this;
        }

        /**
         * 是否适配刘海屏，默认适配
         */
        public Builder setAdaptCutout(boolean adaptCutout) {
            mAdaptCutout = adaptCutout;
            return this;
        }

        public VideoPlayerConfig build() {
            //创建builder对象
            return new VideoPlayerConfig(this);
        }
    }

    public final boolean mPlayOnMobileNetwork;
    public final boolean mEnableOrientation;
    public final boolean mEnableAudioFocus;
    public final boolean mIsEnableLog;
    public final ProgressManager mProgressManager;
    public final PlayerFactory mPlayerFactory;
    public final int mScreenScaleType;
    public final SurfaceFactory mRenderViewFactory;
    public final boolean mAdaptCutout;

    private VideoPlayerConfig(Builder builder) {
        mIsEnableLog = builder.mIsEnableLog;
        mEnableOrientation = builder.mEnableOrientation;
        mPlayOnMobileNetwork = builder.mPlayOnMobileNetwork;
        mEnableAudioFocus = builder.mEnableAudioFocus;
        mProgressManager = builder.mProgressManager;
        mScreenScaleType = builder.mScreenScaleType;
        if (builder.mPlayerFactory == null) {
            //默认为AndroidMediaPlayer
            mPlayerFactory = MediaPlayerFactory.create();
        } else {
            mPlayerFactory = builder.mPlayerFactory;
        }
        if (builder.mRenderViewFactory == null) {
            //默认使用TextureView渲染视频
            mRenderViewFactory = TextureViewFactory.create();
        } else {
            mRenderViewFactory = builder.mRenderViewFactory;
        }
        mAdaptCutout = builder.mAdaptCutout;
    }


}
