package org.yczbj.ycvideoplayer.ui.test3.download;

import android.content.ContentValues;

/**
 * Created by yc on 2018/1/17.
 */

public class DLTasksManagerModel {

    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String URL = "url";
    public final static String PATH = "path";

    private int id;
    private String logo;
    private String name;
    private String url;
    private String path;

    public DLTasksManagerModel() {

    }

    public DLTasksManagerModel(int id, String logo, String name, String url, String path) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.url = url;
        this.path = path;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(NAME, name);
        cv.put(URL, url);
        cv.put(PATH, path);
        return cv;
    }

}
