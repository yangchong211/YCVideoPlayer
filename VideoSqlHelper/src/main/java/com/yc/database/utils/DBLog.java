package com.yc.database.utils;

import android.util.Log;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 日志工具类
 *     revise:
 * </pre>
 */
public class DBLog {
	/**
	 * 日志标签
	 */
	private static final String TAG = "YCSqlLog";
	/**
	 * 是否调试模式
	 */
	private static boolean IS_DEBUG = true;
	
	public static boolean isDebug() {
		return IS_DEBUG;
	}
	
	/**
	 * 是否开启调试模式
	 * @param enable
	 */
	public static final void debugEnable(boolean enable) {
		IS_DEBUG = enable;
	}
	
	public static final void debug(Object msg) {
		debug(msg.toString());
	}
	
	public static final void debug(String msg) {
		if(IS_DEBUG) {
			Log.i(TAG, msg);
		}
	}
	
	public static final void debug(String msg, Throwable e) {
		if(IS_DEBUG) {
			Log.i(TAG, msg, e);
		}
	}
	
	public static final void debugSql(String sql, Object[] params) {
		if(IS_DEBUG) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < params.length; i++) {
				if(sb.length() <= 0) {
					sb.append(",");
				}
				sb.append(params.toString());
			}
			Log.i(TAG, "{SQL:" + sql + ",PARAMS：" + sb.toString() + "}");
		}
	}
	
	public static final void debugSql(String sql, Object[] params, Throwable e) {
		if(IS_DEBUG) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < params.length; i++) {
				if(sb.length() <= 0) {
					sb.append(",");
				}
				sb.append(params.toString());
			}
			Log.i(TAG, "{SQL:" + sql + ",PARAMS：" + sb.toString() + "}", e);
		}
	}
	
	public static final void debug(String format, Object...objects) {
		debug(String.format(format, objects));
	}
}
