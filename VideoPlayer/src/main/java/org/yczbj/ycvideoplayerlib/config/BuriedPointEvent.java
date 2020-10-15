package org.yczbj.ycvideoplayerlib.config;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/1/29
 *     desc  : 视频全局埋点事件
 *     revise:
 * </pre>
 */
public interface BuriedPointEvent {

    /**
     * 进入视频播放
     * @param url                       视频url
     */
    void playerIn(String url);

    /**
     * 退出视频播放
     * @param url                       视频url
     */
    void playerDestroy(String url);

    /**
     * 视频播放完成
     * @param url                       视频url
     */
    void playerCompletion(String url);

    /**
     * 视频播放异常
     * @param url                       视频url
     * @param isNetError                是否是网络异常
     */
    void onError(String url , boolean isNetError);

    /**
     * 点击了视频广告
     * @param url                       视频url
     */
    void clickAd(String url);

    /**
     * 退出视频播放时候的播放进度百度分
     * @param url                       视频url
     * @param progress                  视频进度，计算百分比【退出时候进度 / 总进度】
     */
    void playerOutProgress(String url , float progress);

    /**
     * 视频切换音频
     * @param url                       视频url
     */
    void videoToMedia(String url);

}
