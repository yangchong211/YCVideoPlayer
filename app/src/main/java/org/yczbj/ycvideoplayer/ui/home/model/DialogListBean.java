package org.yczbj.ycvideoplayer.ui.home.model;

/**
 * Created by yc on 2018/1/10.
 */

public class DialogListBean {

    private String logo;
    private String name;
    private String title;
    private String video;
    private int id;

    public DialogListBean(String logo, String name, String title , String video) {
        this.logo = logo;
        this.name = name;
        this.title = title;
        this.video = video;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
