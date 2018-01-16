package org.yczbj.ycvideoplayer.ui.special.model;

/**
 * Created by yc on 2018/1/12.
 */

public class SpecialBean {

    private String title;
    private String content;
    private String time;
    private String author;
    private int id;

    public SpecialBean(String title, String content, String time, String author, int id) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.author = author;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
