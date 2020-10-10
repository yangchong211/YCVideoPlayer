package com.yc.kernel.impl.exo;

import android.content.Context;

import com.yc.kernel.factory.PlayerFactory;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : exo视频播放器Factory
 *     revise:
 * </pre>
 */
public class ExoPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    public static ExoPlayerFactory create() {
        return new ExoPlayerFactory();
    }

    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
