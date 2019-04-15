package org.yczbj.ycvideoplayerlib.inter.listener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/3/9
 *     desc  : 视频播放模式监听
 *     revise:
 * </pre>
 */
public interface OnPlayerTypeListener {

    /**
     * 切换到全屏播放监听
     */
    void onFullScreen();

    /**
     * 切换到小窗口播放监听
     */
    void onTinyWindow();

    /**
     * 切换到正常播放监听
     */
    void onNormal();

}
