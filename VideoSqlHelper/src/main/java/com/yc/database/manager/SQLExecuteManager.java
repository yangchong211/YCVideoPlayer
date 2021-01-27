package com.yc.database.manager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.io.Serializable;
import java.util.Date;

import com.yc.database.bean.BindSQL;
import com.yc.database.utils.DBLog;
import com.yc.database.utils.DateUtil;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : SQL语句执行器
 *     revise:
 * </pre>
 */
public class SQLExecuteManager implements Serializable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * SQLite中的关键字 
	 */
	public static final String[] SQLITE_KEYWORDS = { "ABORT", "ACTION",
		"ADD", "AFTER", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC",
		"ATTACH", "AUTOINCREMENT", "BEFORE", "BEGIN", "BETWEEN", "BY",
		"CASCADE", "CASE", "CAST", "CHECK", "COLLATE", "COLUMN", "COMMIT",
		"CONFLICT", "CONSTRAINT", "CREATE", "CROSS", "CURRENT_DATE",
		"CURRENT_TIME", "CURRENT_TIMESTAMP", "DATABASE", "DEFAULT",
		"DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DETACH", "DISTINCT",
		"DROP", "EACH", "ELSE", "END", "ESCAPE", "EXCEPT", "EXCLUSIVE",
		"EXISTS", "EXPLAIN", "FAIL", "FOR", "FOREIGN", "FROM", "FULL",
		"GLOB", "GROUP", "HAVING", "IF", "IGNORE", "IMMEDIATE", "IN",
		"INDEX", "INDEXED", "INITIALLY", "INNER", "INSERT", "INSTEAD",
		"INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "KEY", "LEFT", "LIKE",
		"LIMIT", "MATCH", "NATURAL", "NO", "NOT", "NOTNULL", "NULL", "OF",
		"OFFSET", "ON", "OR", "ORDER", "OUTER", "PLAN", "PRAGMA",
		"PRIMARY", "QUERY", "RAISE", "REFERENCES", "REGEXP", "REINDEX",
		"RELEASE", "RENAME", "REPLACE", "RESTRICT", "RIGHT", "ROLLBACK",
		"ROW", "SAVEPOINT", "SELECT", "SET", "TABLE", "TEMP", "TEMPORARY",
		"THEN", "TO", "TRANSACTION", "TRIGGER", "UNION", "UNIQUE",
		"UPDATE", "USING", "VACUUM", "VALUES", "VIEW", "VIRTUAL", "WHEN",
		"WHERE" };
	
	/**
	 * 数据库操作类
	 */
	private SQLiteDatabase mSQLiteDataBase;

	public SQLExecuteManager(SQLiteDatabase mSQLiteDataBase) {
		super();
		this.mSQLiteDataBase = mSQLiteDataBase;
	}
	
	/**
	 * 开启一个事务(事务开始)
	 * 在事务代码执行完成后，必须要执行successTransaction()将事务标记为成功
	 * 在代码的最后必须要执行endTransaction()来结束当前事务，如果事务成功则提交事务，否则回滚事务
	 * <pre>
	 *   db.beginTransaction();
	 *   try {
	 *     ...
	 *     db.setTransactionSuccessful();
	 *   } finally {
	 *     db.endTransaction();
	 *   }
	 * </pre>
	 */
	public void beginTransaction() {
		this.mSQLiteDataBase.beginTransaction();
	}
	
	/**
	 * 标记当前事务成功
	 */
	public void successTransaction() {
		this.mSQLiteDataBase.setTransactionSuccessful();
	}
	
	/**
	 * 结束当前事务，当事物被标记成功后，此操作会提交事务，否则会回滚事务
	 */
	public void endTransaction() {
		this.mSQLiteDataBase.endTransaction();
	}

	/**
	 * 执行指定无返回值的单条SQL语句，如建表、创建数据库等
	 * @param sql						执行sql
	 */
	public void execSQL(String sql) {
		DBLog.debug(sql);
		this.mSQLiteDataBase.execSQL(sql);
	}
	
	/**
	 * 插入一条记录，返回该记录的rowId
	 * @param sql
	 * @param args
	 * @return				插入失败返回-1，成功返回rowId
	 */
	public long insert(String sql, Object[] args) {
		long rowId = -1;
		SQLiteStatement statement = this.mSQLiteDataBase.compileStatement(sql);
		try {
			if(args != null) {
				for(int i = 0; i < args.length; i++) {
					bindArgs(statement, i + 1, args[i]);
				}
			}
			rowId = statement.executeInsert();
			DBLog.debug(sql, args);
		} finally {
			statement.close();
		}
		return rowId;
	}
	
	/**
	 * 根据BindSQL进行插入数据
	 * @param bindSQL
	 * @return
	 * @throws Exception
	 */
	public long insert(BindSQL bindSQL) {
		if (bindSQL==null){
			return -1;
		}
		String sql = bindSQL.getSql();
		Object[] bindArgs = bindSQL.getBindArgs();
		return insert(sql, bindArgs);
	}
	
	/**
	 * 绑定参数
	 * @param statement
	 * @param position
	 * @param args
	 */
	private void bindArgs(SQLiteStatement statement, int position, Object args) {
		int type = FieldTypeManager.getValueType(args);
		switch(type) {
			case FieldTypeManager.VALUE_TYPE_NULL:
				statement.bindNull(position);
				break;
			case FieldTypeManager.BASE_TYPE_BYTE_ARRAY:
				statement.bindBlob(position, (byte[])args);
				break;
			case FieldTypeManager.BASE_TYPE_CHAR:
			case FieldTypeManager.BASE_TYPE_STRING:
				statement.bindString(position, args.toString());
				break;
			case FieldTypeManager.BASE_TYPE_DATE:
				statement.bindString(position, DateUtil.formatDatetime((Date) args));
				break;
			case FieldTypeManager.BASE_TYPE_DOUBLE:
			case FieldTypeManager.BASE_TYPE_FLOAT:
				statement.bindDouble(position, Double.parseDouble(args.toString()));
				break;
			case FieldTypeManager.BASE_TYPE_INT:
			case FieldTypeManager.BASE_TYPE_LONG:
			case FieldTypeManager.BASE_TYPE_SHORT:
				statement.bindLong(position, Long.parseLong(args.toString()));
				break;
			case FieldTypeManager.NOT_BASE_TYPE:
				throw new IllegalArgumentException("未知参数类型，请检查绑定参数");
		}
	}
	
	/**
	 * 删除指定表
	 * @param tableName
	 * @throws Exception
	 */
	public void dropTable(String tableName) {
		String sql = "DROP TABLE IF EXISTS " + tableName;
		execSQL(sql);
	}
	
	/**
	 * 删除,表名不能使用占位符
	 * @param bindSQL	
	 */
	public void delete(BindSQL bindSQL) {
		updateOrDelete(bindSQL.getSql(), bindSQL.getBindArgs());
	}
	
	/**
	 * 删除,表名不能使用占位符
	 * @param sql		删除语句(参数使用占位符)
	 * @param args		占位符参数
	 */
	@SuppressLint("NewApi")
	public void updateOrDelete(String sql, Object[] args) {
		SQLiteStatement statement = mSQLiteDataBase.compileStatement(sql);
		try {
			if(args != null) {
				for(int i = 0; i < args.length; i++) {
					bindArgs(statement, i + 1, args[i]);
				}
			}
			DBLog.debug(sql, args);
			statement.executeUpdateDelete();
		} finally {
			statement.close();
		}
	}
	
	/**
	 * 删除(对于表名需要动态获取的，此方法非常适合)
	 * @param tableName			要删除的数据表
	 * @param whereClause		where后面的条件句(delete from XXX where XXX)，参数使用占位符
	 * @param whereArgs			where子句后面的占位符参数
	 */
	public void delete(String tableName, String whereClause, String[] whereArgs) {
		DBLog.debug("{SQL：DELETE FROM " + tableName + " WHERE " + whereClause + "，PARAMS：" + whereArgs + "}");
		mSQLiteDataBase.delete(tableName, whereClause, whereArgs);
	}
	
	/**
	 * 更新
	 * @param bindSQL
	 */
	public void update(BindSQL bindSQL) {
		updateOrDelete(bindSQL.getSql(), bindSQL.getBindArgs());
	}
	
	/**
	 * 根据SQL进行查询
	 * @param sql
	 * @return
	 */
	public Cursor query(String sql) {
		return query(sql, null);
	}
	
	/**
	 * 执行绑定语句
	 * 运行一个预置的SQL语句，返回带游标的数据集（与query的语句最大的区别 = 防止SQL注入）
	 * @param sql								sql语句
	 * @param whereArgs							搜索条件
	 * @return
	 */
	public Cursor query(String sql, String[] whereArgs) {
		DBLog.debug("{SQL：" + sql + "，PARAMS：" + whereArgs + "}");
		return this.mSQLiteDataBase.rawQuery(sql, whereArgs); 
	}

	/**
	 * 根据BindSQL查询
	 * @param bindSQL
	 * @return
	 */
	public Cursor query(BindSQL bindSQL) {
		return query(bindSQL.getSql(), (String[])bindSQL.getBindArgs());
	}

	@Deprecated
	public Cursor query(boolean distinct, String table, String[] columns,
						String selection, String[] selectionArgs, String groupBy,
						String having, String orderBy, String limit) {
		// 查询指定的数据表返回一个带游标的数据集。
		// 各参数说明：
		// table：表名称
		// colums：列名称数组
		// selection：条件子句，相当于where
		// selectionArgs：条件语句的参数数组
		// groupBy：分组
		// having：分组条件
		// orderBy：排序类
		// limit：分页查询的限制
		return this.mSQLiteDataBase.query(distinct, table, columns, selection,
				selectionArgs, groupBy, having, orderBy, limit);
	}

}
