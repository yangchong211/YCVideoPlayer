package org.yczbj.ycvideoplayerlib.player;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.yc.kernel.impl.media.MediaPlayerFactory;

import org.yczbj.ycvideoplayerlib.config.PlayerConfig;
import org.yczbj.ycvideoplayerlib.surface.TextureViewFactory;

public class VideoPlayerBuilder {

    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {

        private int mColor = 0;
        private int[] mTinyScreenSize;
        private int mCurrentPosition = -1;

        /**
         * 设置视频播放器的背景色
         * @param color                         color
         * @return                              Builder
         */
        public Builder setPlayerBackgroundColor(@ColorInt int color) {
            //使用注解限定福
            if (color==0){
                this.mColor = Color.BLACK;
            } else {
                this.mColor = color;
            }
            return this;
        }

        /**
         * 设置小屏的宽高
         * @param tinyScreenSize                其中tinyScreenSize[0]是宽，tinyScreenSize[1]是高
         * @return                              Builder
         */
        public Builder setTinyScreenSize(int[] tinyScreenSize) {
            this.mTinyScreenSize = tinyScreenSize;
            return this;
        }

        /**
         * 一开始播放就seek到预先设置好的位置
         * @param position                      位置
         * @return                              Builder
         */
        public Builder skipPositionWhenPlay(int position) {
            this.mCurrentPosition = position;
            return this;
        }


        public VideoPlayerBuilder build() {
            //创建builder对象
            return new VideoPlayerBuilder(this);
        }
    }


    public final int mColor;
    public final int[] mTinyScreenSize;
    public final int mCurrentPosition;

    public VideoPlayerBuilder(Builder builder) {
        mColor = builder.mColor;
        mTinyScreenSize = builder.mTinyScreenSize;
        mCurrentPosition = builder.mCurrentPosition;
    }


}
