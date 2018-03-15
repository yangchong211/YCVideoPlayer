package org.yczbj.ycvideoplayerlib;

/**
 * 视频顶部点击事件
 */

public interface OnVideoControlListener {

    /**
     * 视频顶部点击事件【下载，切换音频，分享等】
     *
     * @param type      切换到的清晰度的索引值
     */
    void onVideoControlClick(int type);
    
}
