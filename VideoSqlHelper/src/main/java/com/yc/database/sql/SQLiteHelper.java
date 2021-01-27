package com.yc.database.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 继承自SQLiteOpenHelper，扩展实现自定义db的生成路径
 *     revise: 用作sql存取数据的基础功能库，暂时不想依赖greenDao(插件+100kb库)或者realm(2M)数据库【对于视频播放器库，避免组件体积过大】
 * </pre>
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	/**
	 * 默认db，解决在onCreate，onUpgrade中执行其他操作数据库操作时出现的异常
	 * (java.lang.IllegalStateException: getDatabase called recursively)
	 */
	private SQLiteDatabase mDefaultSQLiteDatabase = null;
	/**
	 * 数据库配置
	 */
	private SQLiteDBConfig mConfig;
	
	public SQLiteHelper(SQLiteDBConfig config) {
		this(new SQLiteContext(config.getContext(), config), config.getDbName(), null, config.getVersion());
		this.mConfig = config;
	}

	/**
	 * 创建数据库对象
	 * @param context							上下文
	 * @param name								名称
	 * @param factory							factory
	 * @param version							版本号
	 */
	public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		// 参数说明
		// context：上下文对象
		// name：数据库名称
		// param：一个可选的游标工厂（通常是 Null）
		// version：当前数据库的版本，值必须是整数并且是递增的状态

		// 必须通过super调用父类的构造函数
		super(context, name, factory, version);
	}

	/**
	 * 创建数据库对象
	 * @param context							上下文
	 * @param name								名称
	 * @param factory							factory
	 * @param version							版本号
	 * @param errorHandler						errorHandler
	 */
	public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
						int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	/**
	 * 创建 or 打开 可读/写的数据库（通过 返回的SQLiteDatabase对象 进行操作）
	 * 对于操作 = “增、删、改（更新）”，需获得 可"读 / 写"的权限：getWritableDatabase()
	 * @return									SQLiteDatabase对象
	 */
	@Override
	public SQLiteDatabase getWritableDatabase() {
		if(mDefaultSQLiteDatabase != null) {
			return mDefaultSQLiteDatabase;
		}
		return super.getWritableDatabase();
	}

	/**
	 * 创建 or 打开 可读的数据库（通过 返回的SQLiteDatabase对象 进行操作
	 * 对于操作 = “查询”，需获得 可"读 "的权限getReadableDatabase()
	 * @return
	 */
	@Override
	public SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	/**
	 * 创建数据库调用该方法
	 * 数据库第1次创建时 则会调用，即 第1次调用 getWritableDatabase（） / getReadableDatabase（）时调用
	 * 调用时刻：当数据库第1次创建时调用
	 * 作用：创建数据库 表 & 初始化数据
	 * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
	 * @param db									db数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.mDefaultSQLiteDatabase = db;
		if(mConfig.getDbListener() != null) {
			mConfig.getDbListener().onDbCreateHandler(db);
		}
	}

	/**
	 * 关闭数据库
	 */
	@Override
	public synchronized void close() {
		super.close();
	}

	/**
	 * 更新数据库
	 * 数据库升级时自动调用
	 * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
	 * 作用：更新数据库表结构
	 * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
	 * @param db									db数据库
	 * @param oldVersion							老版本
	 * @param newVersion							新版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.mDefaultSQLiteDatabase = db;
		if(mConfig.getDbListener() != null) {
			mConfig.getDbListener().onUpgradeHandler(db, oldVersion, newVersion);
		}
	}

	@Override
	public String getDatabaseName() {
		//获取数据库名称
		return super.getDatabaseName();
	}
}
