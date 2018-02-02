package org.yczbj.ycvideoplayer.ui.test3.download2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.yczbj.ycvideoplayer.ui.test3.download.DLTasksManagerModel;

/**
 * Created by yc on 2018/1/17.
 */

public class DlTasksManagerDBOpenHelper extends SQLiteOpenHelper {

    /**
     * 这个是数据库的数据库名称
     */
    private final static String DATABASE_NAME = "dlTasksManger2.db";
    private final static int DATABASE_VERSION = 2;

    DlTasksManagerDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("CREATE TABLE IF NOT EXISTS "
                + DlTasksManagerDBController.TABLE_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , DLTasksManagerModel.ID
                , DLTasksManagerModel.LOGO
                , DLTasksManagerModel.NAME
                , DLTasksManagerModel.URL
                , DLTasksManagerModel.PATH
        ));*/

        String sql = "create table if not exists dlTasksManger2(" +
                "_id integer primary key autoincrement," +
                "id integer," +
                "logo varchar(200)," +
                "name varchar(200)," +
                "url  varchar(200)," +
                "path verchar(200)," +
                ")";
        db.execSQL(sql);

        sql = "create table if not exists downloaded2(" +
                "_id integer primary key autoincrement," +
                "id integer," +
                "logo varchar(200)," +
                "name varchar(200)," +
                "url  varchar(200)," +
                "path verchar(200)," +
                ")";
        db.execSQL(sql);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        /*db.execSQL("CREATE TABLE IF NOT EXISTS "
                + DlTasksManagerDBController.DOWNLOADED_NAME
                + String.format(
                "("
                        + "%s INTEGER PRIMARY KEY, " // id, download id
                        + "%s VARCHAR, " // name
                        + "%s VARCHAR, " // url
                        + "%s VARCHAR " // path
                        + ")"
                , DLTasksManagerModel.ID
                , DLTasksManagerModel.LOGO
                , DLTasksManagerModel.NAME
                , DLTasksManagerModel.URL
                , DLTasksManagerModel.PATH
        ));*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.delete(DlTasksManagerDBController.TABLE_NAME, null, null);
        }
    }


}
