package org.yczbj.ycvideoplayerlib.player;

import org.yczbj.ycvideoplayerlib.config.ConstantKeys;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 播放器监听实现类
 *     revise: 空实现。用的时候只需要重写需要的方法
 * </pre>
 */
public class SimpleStateListener implements OnVideoStateListener {

    /**
     * 播放模式
     * 普通模式，小窗口模式，正常模式三种其中一种
     * MODE_NORMAL              普通模式
     * MODE_FULL_SCREEN         全屏模式
     * MODE_TINY_WINDOW         小屏模式
     * @param playerState                       播放模式
     */
    @Override
    public void onPlayerStateChanged(@ConstantKeys.PlayModeType int playerState) {

    }

    /**
     * 播放状态
     * -1               播放错误
     * 0                播放未开始
     * 1                播放准备中
     * 2                播放准备就绪
     * 3                正在播放
     * 4                暂停播放
     * 5                正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
     * 6                暂停缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停
     * 7                播放完成
     * 8                开始播放中止
     * @param playState                         播放状态，主要是指播放器的各种状态
     */
    @Override
    public void onPlayStateChanged(int playState) {

    }

}
