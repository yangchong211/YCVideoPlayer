package org.yczbj.ycvideoplayerlib.kernel.impl.exo;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.kernel.player.PlayerFactory;


public class ExoMediaPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    public static ExoMediaPlayerFactory create() {
        return new ExoMediaPlayerFactory();
    }

    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
