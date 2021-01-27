package com.yc.database.bean;


/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2017/8/6
 *     desc  : 主键字段
 *     revise:
 * </pre>
 */
public class PrimaryKey extends Property {
	/**
	 * 当前主键是否为自增长
	 */
	private boolean isAutoGenerate;

	public boolean isAutoGenerate() {
		return isAutoGenerate;
	}

	public void setAutoGenerate(boolean isAutoGenerate) {
		this.isAutoGenerate = isAutoGenerate;
	}
}
