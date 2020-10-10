package org.yczbj.ycvideoplayerlib.player.impl.exo;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.player.factory.PlayerFactory;


public class ExoMediaPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    public static ExoMediaPlayerFactory create() {
        return new ExoMediaPlayerFactory();
    }

    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
