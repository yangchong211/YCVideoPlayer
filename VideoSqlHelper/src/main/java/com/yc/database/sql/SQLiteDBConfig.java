package com.yc.database.sql;

import android.content.Context;

import com.yc.database.listener.InterDBListener;
import com.yc.database.listener.SimpleDBListener;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 数据库相关配置
 *     revise:
 * </pre>
 */
public final class SQLiteDBConfig {
	/**
	 * 数据文件的默认存储目录(不包含SDCard目录部分，自动添加)，可手动设置多个数据库的默认目录
	 */
	public static String DEFAULT_DB_DIRECTORY_PATH = "/TigerDB/";
	/**
	 * 默认数据库名，可修改
	 */
	public static String DEFAULT_DB_NAME = "Tiger.db";
	/**
	 * 默认版本号，可修改
	 */
	public static int DEFAULT_VERSION = 1;
	/**
	 * 数据库上下文
	 */
	private Context mContext;
	/**
	 * 当前数据库文件所在目录路径(绝对路径)
	 */
	private String mDbDirectoryPath = DEFAULT_DB_DIRECTORY_PATH;
	/**
	 * 数据库文件名,默认为：Tiger.db
	 */
	private String mDbName = DEFAULT_DB_NAME;
	/**
	 * 当前数据库版本号
	 */
	private int mVersion = DEFAULT_VERSION;
	/**
	 * 数据库监听
	 */
	private InterDBListener mDbListener;
	
	public SQLiteDBConfig(Context context) {
		this.mContext = context;
		mDbListener = new SimpleDBListener();
	}
	
	public SQLiteDBConfig(Context context, String dbName) {
		super();
		this.mContext = context;
		this.mDbName = dbName;
		mDbListener = new SimpleDBListener();
	}
	
	public SQLiteDBConfig(Context context, String dbDirectoryPath, String dbName) {
		super();
		this.mContext = context;
		this.mDbDirectoryPath = dbDirectoryPath;
		this.mDbName = dbName;
		mDbListener = new SimpleDBListener();
	}

	/**
	 * 获取数据库所在的上下文
	 * @return
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * 设置数据库所在的上下文
	 * @param mContext
	 */
	public void setContext(Context mContext) {
		this.mContext = mContext;
	}
	
	public String getDbDirectoryPath() {
		return mDbDirectoryPath;
	}
	
	public void setDbDirectoryPath(String mDbDirectoryPath) {
		this.mDbDirectoryPath = mDbDirectoryPath;
	}
	
	public String getDbName() {
		return mDbName;
	}
	
	public void setDbName(String mDbName) {
		this.mDbName = mDbName;
	}
	
	public int getVersion() {
		return mVersion;
	}
	
	public void setVersion(int mVersion) {
		this.mVersion = mVersion;
	}

	public InterDBListener getDbListener() {
		return mDbListener;
	}

	public void setDbListener(InterDBListener mDbListener) {
		this.mDbListener = mDbListener;
	}
}
