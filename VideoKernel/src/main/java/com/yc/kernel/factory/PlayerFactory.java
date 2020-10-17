package com.yc.kernel.factory;

import android.content.Context;

import com.yc.kernel.inter.AbstractVideoPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 此接口使用方法
 *     revise: 1.继承{@link AbstractVideoPlayer}扩展自己的播放器。
 *             2.继承此接口并实现{@link #createPlayer(Context)}，返回步骤1中的播放器。
 * </pre>
 */
public abstract class PlayerFactory<T extends AbstractVideoPlayer> {

    /**
     * 创建具体的内核播放器Player
     * @param context                       上下文
     * @return                              具体的player
     */
    public abstract T createPlayer(Context context);

}
