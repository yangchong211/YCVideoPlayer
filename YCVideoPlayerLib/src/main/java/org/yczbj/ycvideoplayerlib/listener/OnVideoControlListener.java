package org.yczbj.ycvideoplayerlib.listener;

/**
 * 视频顶部点击事件
 */

public interface OnVideoControlListener {

    /**
     * 视频顶部点击事件【下载，切换音频，分享等】
     * @param type      类型
     *                  1.DOWNLOAD，下载
     *                  2.AUDIO，切换音频
     *                  3.SHARE，分享
     */
    void onVideoControlClick(int type);
    
}
