package com.adtec.rdc.base.common.util;

/**
 * 防SQL注入工具类
 * @author JTao
 *
 */
public class SqlEscapeUtils {
	/**
	 * 替换单个关键字或参数中的特殊字段
	 * @param str
	 * @return
	 */
	public static String escapeSingleKey(String str) {
		if(str == null) {
			return str;
		}
		return str.replace(" ", "");
	}
}
