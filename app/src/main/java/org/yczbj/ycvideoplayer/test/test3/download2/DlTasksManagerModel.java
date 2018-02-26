package org.yczbj.ycvideoplayer.test.test3.download2;

import android.content.ContentValues;

/**
 * Created by yc on 2018/1/17.
 */

public class DlTasksManagerModel {

    public final static String ID = "id";
    public final static String LOGO = "logo";
    public final static String NAME = "name";
    public final static String URL = "url";
    public final static String PATH = "path";
    public final static String TYPE = "type";

    private int id;
    private String logo;
    private String name;
    private String url;
    private String path;
    /**
     * type表示类型：1是正在下载中数据；2是已经完成下载数据，其他则是默认为1类型
     */
    private int type;

    public DlTasksManagerModel() {

    }

    public DlTasksManagerModel(int id, String logo, String name, String url, String path , int type) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.url = url;
        this.path = path;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(LOGO, logo);
        cv.put(NAME, name);
        cv.put(URL, url);
        cv.put(PATH, path);
        cv.put(TYPE, type);
        return cv;
    }

}
