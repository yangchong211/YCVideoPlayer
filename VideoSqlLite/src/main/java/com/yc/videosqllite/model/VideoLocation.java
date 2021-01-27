package com.yc.videosqllite.model;


import java.io.Serializable;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2020/8/6
 *     desc  : 音视频bean
 *     revise: 必须
 * </pre>
 */
public class VideoLocation implements Serializable {

    /**
     * 视频链接
     */
    private String url;
    /**
     * 视频链接md5
     */
    private String urlMd5;
    /**
     * 视频播放位置
     */
    private long position;
    /**
     * 视频总时间
     */
    private long totalTime;

    public VideoLocation(String url, long position, long totalTime) {
        this.url = url;
        this.position = position;
        this.totalTime = totalTime;
    }

    /*public VideoLocation(String url, String urlMd5, long position, long totalTime) {
        this.url = url;
        this.urlMd5 = urlMd5;
        this.position = position;
        this.totalTime = totalTime;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "VideoLocation{" +
                "url='" + url + '\'' +
                ", urlMd5='" + urlMd5 + '\'' +
                ", position=" + position +
                ", totalTime=" + totalTime +
                '}';
    }


}
