package com.yc.database.utils;

public class ValueUtil {

	public static boolean isEmpty(String value) {
		if(value == null || value.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Object value) {
		if(value == null) {
			return true;
		}
		return isEmpty(value.toString());
	}
}
