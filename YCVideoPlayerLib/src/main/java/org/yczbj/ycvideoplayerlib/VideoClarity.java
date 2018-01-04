package org.yczbj.ycvideoplayerlib;

/**
 * 清晰度
 */
public class VideoClarity {

    private String grade;           // 清晰度等级
    private String p;               // 270P、480P、720P、1080P、4K ...
    private String videoUrl;        // 视频链接地址

    public VideoClarity(String grade, String p, String videoUrl) {
        this.grade = grade;
        this.p = p;
        this.videoUrl = videoUrl;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}