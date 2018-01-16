package org.yczbj.ycvideoplayer.download;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/1/9
 * 描    述：下载Tasks帮助类
 * 修订历史：
 *          下载框架：https://github.com/lingochamp/FileDownloader
 * ================================================
 */
public class TasksManagerDBController {

    final static String TABLE_NAME = "tasksManger";
    private final SQLiteDatabase db;

    TasksManagerDBController() {
        TasksManagerDBOpenHelper openHelper = new TasksManagerDBOpenHelper(BaseApplication.getInstance());
        db = openHelper.getWritableDatabase();
    }

    public List<TasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        final List<TasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }
            do {
                TasksManagerModel model = new TasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }


    public TasksManagerModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        // 必须使用文件下载器，将TasksManagerModel与文件下载器关联
        final int id = FileDownloadUtils.generateId(url, path);
        TasksManagerModel model = new TasksManagerModel();
        model.setId(id);
        model.setName(url.substring(url.lastIndexOf("/")));
        model.setUrl(url);
        model.setPath(path);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        return succeed ? model : null;
    }


}
