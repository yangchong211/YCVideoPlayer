package org.yczbj.ycvideoplayerlib.player.impl.media;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.player.factory.PlayerFactory;

/**
 * 创建{@link AndroidMediaPlayer}的工厂类，不推荐，系统的MediaPlayer兼容性较差，建议使用IjkPlayer或者ExoPlayer
 */
public class MediaPlayerFactory extends PlayerFactory<AndroidMediaPlayer> {

    public static MediaPlayerFactory create() {
        return new MediaPlayerFactory();
    }

    @Override
    public AndroidMediaPlayer createPlayer(Context context) {
        return new AndroidMediaPlayer(context);
    }
}
