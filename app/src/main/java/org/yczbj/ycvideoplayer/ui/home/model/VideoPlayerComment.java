package org.yczbj.ycvideoplayer.ui.home.model;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2018/1/9
 * 描    述：视频播放器详情页面评论实体类
 * 修订历史：
 * ================================================
 */
public class VideoPlayerComment {

    private int userLogo;
    private String name;
    private int zan;
    private String content;

    public VideoPlayerComment(int userLogo, String name, int zan, String content) {
        this.userLogo = userLogo;
        this.name = name;
        this.zan = zan;
        this.content = content;
    }

    public int getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(int userLogo) {
        this.userLogo = userLogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
