package org.yczbj.ycvideoplayer.ui.test.test3.download2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2018/1/17.
 */

public class DlTasksManagerDBController {

    /**
     * 这个是字典表的表名
     */
    final static String TABLE_NAME = "dlTasksManger2";
    private final static String DOWNLOADED_NAME = "downloaded2";
    private final SQLiteDatabase db;

    DlTasksManagerDBController() {
        DlTasksManagerDBOpenHelper openHelper = new DlTasksManagerDBOpenHelper(BaseApplication.getInstance());
        db = openHelper.getWritableDatabase();
    }

    /**
     * 从数据库中获取所有值
     * 目前是正在下载的数据存储到数据库
     * 思考：cursor类如何获取布尔类型数据
     */
    public List<DlTasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        final List<DlTasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }
            do {
                DlTasksManagerModel model = new DlTasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(DlTasksManagerModel.ID)));
                model.setLogo(c.getString(c.getColumnIndex(DlTasksManagerModel.LOGO)));
                model.setName(c.getString(c.getColumnIndex(DlTasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(DlTasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(DlTasksManagerModel.PATH)));
                model.setType(c.getInt(c.getColumnIndex(DlTasksManagerModel.TYPE)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }

    /**
     * 从数据库中获取所有值
     * 目前是已经完成下载的数据
     */
    public List<DlTasksManagerModel> getAllDownloadedList() {
        final Cursor c = db.rawQuery("SELECT * FROM " + DOWNLOADED_NAME, null);
        final List<DlTasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }
            do {
                DlTasksManagerModel model = new DlTasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(DlTasksManagerModel.ID)));
                model.setLogo(c.getString(c.getColumnIndex(DlTasksManagerModel.LOGO)));
                model.setName(c.getString(c.getColumnIndex(DlTasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(DlTasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(DlTasksManagerModel.PATH)));
                model.setType(c.getInt(c.getColumnIndex(DlTasksManagerModel.TYPE)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }


    DlTasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            ToastUtils.showShort("链接或者路径不能为空");
            return null;
        }
        // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
        final int id = FileDownloadUtils.generateId(url, path);
        DlTasksManagerModel model = new DlTasksManagerModel();
        model.setId(id);
        model.setName("name");
        model.setUrl(url);
        model.setPath(path);
        model.setType(1);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }

    /**
     * 从数据库中删除
     * @param url
     * @return
     */
    boolean removeTasks(String url) {
        return db.delete(TABLE_NAME ,"url = ?" ,new String[]{url})!=-1;
    }

    /**
     * 判断数据库中是否存在
     * @param url
     * @return
     */
    boolean isExistDownloadFile(String url) {
        Cursor c = db.query(DOWNLOADED_NAME,null,"url= ?", new String[]{url},null,null,null);
        try{
            return c.moveToNext();
        }finally {
            if (c!=null) {
                c.close();
            }
        }
    }

    /**
     * 添加到已经完成下载数据库
     * @param model
     * @return
     */
    boolean addDownloaded(DlTasksManagerModel model) {
        return db.insert(DOWNLOADED_NAME ,null ,model.toContentValues()) != -1;
    }

    /**
     * 从已经完成数据库中移除某一条数据
     * @param url
     * @return
     */
    boolean removeDownloaded(String url){
        return db.delete(DOWNLOADED_NAME ,"url = ?" ,new String[]{url})!=-1;
    }

}
