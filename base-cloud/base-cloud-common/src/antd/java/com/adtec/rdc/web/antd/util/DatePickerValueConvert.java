package com.adtec.rdc.web.antd.util;

/**
 * 前端控件日期字符串转换
 * like from 2020-06-17T02:15:19.733Z to 20200617
 * @author JTao
 *
 */
public class DatePickerValueConvert {
	public static String convert(String dateString) {
		if(dateString == null || "".equals(dateString)) {
			return dateString;
		}
		if(dateString.length()!=24) {
			return dateString;
		}
		dateString = dateString.replace("-", "");
		dateString = dateString.substring(0, 8);
		return dateString;
	}
}
