package com.yc.database.listener;

import android.database.sqlite.SQLiteDatabase;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 数据库监听空实现
 *     revise:
 * </pre>
 */
public class SimpleDBListener implements InterDBListener {

	@Override
	public void onUpgradeHandler(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onDbCreateHandler(SQLiteDatabase db) {

	}

}
