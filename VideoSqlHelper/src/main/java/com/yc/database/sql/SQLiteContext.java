package com.yc.database.sql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;

import java.io.File;

import com.yc.database.utils.ValueUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 自定义数据库创建容器(设置数据库创建的自定义目录)
 *     revise:
 * </pre>
 */
public class SQLiteContext extends ContextWrapper {

	private SQLiteDBConfig config;
	
	public SQLiteContext(Context base, SQLiteDBConfig config) {
		super(base);
		this.config = config;
	}
	
	@Override
	public File getDatabasePath(String name) {
		if (ValueUtil.isEmpty(config.getDbDirectoryPath())) {
			return super.getDatabasePath(name);
		}
		String phoneRootPath = getPhoneRootPath();
		String[] files = null;
		if(phoneRootPath.startsWith("/")) {
			files = phoneRootPath.substring(1).split("/");
		} else {
			files = phoneRootPath.split("/");
		}
		boolean flag = false;
		for(int i = 0; i < files.length; i++) {
			if(config.getDbDirectoryPath().contains(files[i])) {
				flag = true;
				break;
			}
		}
		String dbPath = config.getDbDirectoryPath();
		if(flag == false) {
			dbPath = phoneRootPath + config.getDbDirectoryPath();
		}
		if(!config.getDbDirectoryPath().endsWith("/")) {
			dbPath = dbPath + "/";
		}
		dbPath = dbPath + config.getDbName();
		makeParentDir(dbPath);
		return new File(dbPath);
	}
	
	/**
	 * 判断其父目录是否存在，不存在则创建
	 * @param path
	 */
	private void makeParentDir(String path) {
		String parentPath = getParentPath(path);
		File file = new File(parentPath);
		if(!file.exists()) {
			makeParentDir(parentPath);
			file.mkdir();
		}
	}
	
	/**
	 * 获取父级目录
	 * @param path
	 * @return
	 */
	public static String getParentPath(String path) {
		if (path.equals("/")) {
			return path;
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		path = path.substring(0, path.lastIndexOf("/"));
		return path.equals("") ? "/" : path;
	}
	
	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
		if (ValueUtil.isEmpty(config.getDbDirectoryPath())) {
			return super.openOrCreateDatabase(name, mode, factory);
		}
		return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
	}
	
	@SuppressLint("NewApi")
	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               CursorFactory factory, DatabaseErrorHandler errorHandler) {
		if (ValueUtil.isEmpty(config.getDbDirectoryPath())) {
			return super.openOrCreateDatabase(name, mode, factory, errorHandler);
		}
		return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
	}
	
	/**
	 * 获取手机根目录
	 * @return
	 */
	public String getPhoneRootPath() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return Environment.getDataDirectory().getAbsolutePath();
	}
}
