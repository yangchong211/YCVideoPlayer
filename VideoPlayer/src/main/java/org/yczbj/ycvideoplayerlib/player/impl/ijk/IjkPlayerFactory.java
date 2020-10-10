package org.yczbj.ycvideoplayerlib.player.impl.ijk;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.player.factory.PlayerFactory;


public class IjkPlayerFactory extends PlayerFactory<IjkPlayer> {

    public static IjkPlayerFactory create() {
        return new IjkPlayerFactory();
    }

    @Override
    public IjkPlayer createPlayer(Context context) {
        return new IjkPlayer(context);
    }
}
