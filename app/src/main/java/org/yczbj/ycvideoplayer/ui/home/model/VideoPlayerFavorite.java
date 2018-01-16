package org.yczbj.ycvideoplayer.ui.home.model;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2018/1/9
 * 描    述：视频播放器详情页面猜你喜欢实体类
 * 修订历史：
 * ================================================
 */
public class VideoPlayerFavorite {

    private String title;
    private int logoUrl;
    private String url;

    public VideoPlayerFavorite(String title, int logoUrl, String url) {
        this.title = title;
        this.logoUrl = logoUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(int logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
