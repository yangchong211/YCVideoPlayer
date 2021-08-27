package com.yc.audioplayer;

import android.content.Context;


/**
 * 音频播放接口定义
 */
public interface IAudio {

    void init(IPlayListener next, Context context);

    /**
     * 播放数据
     *
     * @param data {@link PlayData}
     */
    void play(PlayData data);

    /**
     * 停止播放
     */
    void stop();

    /**
     * 释放音频内容
     */
    void release();

    /**
     * 暂停播放
     */
    void pause();

    /**
     * 回复播放
     */
    void resumeSpeaking();

    /**
     * 是否正在播放
     *
     * @return true 是 false 否
     */
    boolean isPlaying();

}
