package org.yczbj.ycvideoplayer.test.test3.download;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2018/1/17.
 */

public class DLTasksManagerDBController {

    public final static String TABLE_NAME = "dlTasksManger";
    private final SQLiteDatabase db;

    DLTasksManagerDBController() {
        DLTasksManagerDBOpenHelper openHelper = new DLTasksManagerDBOpenHelper(BaseApplication.getInstance());
        db = openHelper.getWritableDatabase();
    }

    public List<DLTasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        final List<DLTasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }

            do {
                DLTasksManagerModel model = new DLTasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(DLTasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(DLTasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(DLTasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(DLTasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }

    public DLTasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
        final int id = FileDownloadUtils.generateId(url, path);
        DLTasksManagerModel model = new DLTasksManagerModel();
        model.setId(id);
        model.setName("任务-----------");
        model.setUrl(url);
        model.setPath(path);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }


    public boolean removeDownloaded(String url){
        return db.delete(TABLE_NAME ,"url = ?" ,new String[]{url})!=-1;
    }

}
