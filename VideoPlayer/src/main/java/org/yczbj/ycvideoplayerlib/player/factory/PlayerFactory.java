package org.yczbj.ycvideoplayerlib.player.factory;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.player.inter.AbstractPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 此接口使用方法
 *     revise: 1.继承{@link AbstractPlayer}扩展自己的播放器。
 *             2.继承此接口并实现{@link #createPlayer(Context)}，返回步骤1中的播放器。
 * </pre>
 */
public abstract class PlayerFactory<T extends AbstractPlayer> {

    public abstract T createPlayer(Context context);

}
