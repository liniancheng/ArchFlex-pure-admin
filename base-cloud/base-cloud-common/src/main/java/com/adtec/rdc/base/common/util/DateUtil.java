package com.adtec.rdc.base.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	

	/**
     * @param 返回java.sql.Date格式的
     * */
    public static java.sql.Date formatSqlDate(String strDate) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		//避免yyyy-MM-dd型返回null
		if (date.indexOf(":")<0) {
			date = date +" 00:00:00";
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return (date);
	}
	
	/**
	 * 切换字符串的日期格式
	 * @param date
	 * @param from
	 * @param to
	 * @return
	 */
	public static String changeDataPattern(String date, String from, String to){
		Date d = format(date, from);
		if(to == null){
			to = "yyyy-MM-dd";
		}
		return format(d, to);
	}
	
	
	
	public static String getXMinuteAfterTime(int x, String pattern){
		Calendar calendar = Calendar.getInstance (); 
		calendar.add(Calendar.MINUTE, x); 
        return format(calendar.getTime(), pattern);
	}
	
	public static Date getDate(Date date, int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, num);
		return cal.getTime();
	}
	
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		df = new SimpleDateFormat(aMask);
		if (aDate == null) {
			aDate=new Date();
		} 
		returnValue = df.format(aDate);
		return (returnValue);
	}
	
	//上日
	public static String getYesterdayDateString(String pattern){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//上月同期
	public static String getPrevMonthTodayString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
	    return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//上季同期
	public static String getPrevSeasonTodayString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//上年同期
	public static String getPrevYearTodayString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	
	//本月初
	public static String getMonthFirstDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
	    return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//本季初
	public static String getSeasonFirstDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH) % 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//本年初
	public static String getYearFirstDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	
	//上月末
	public static String getPrevMonthLastDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
	    return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//上季末
	public static String getPrevSeasonLastDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH) % 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	//上年末
	public static String getPrevYearLastDateString(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	
	public static void main(String[] args) {
		String pattern = "yyyy-MM-dd";
		System.out.println("上日="+getYesterdayDateString(pattern));
		System.out.println("本月初="+getMonthFirstDateString(pattern));
		System.out.println("本季初="+getSeasonFirstDateString(pattern));
		System.out.println("本年初="+getYearFirstDateString(pattern));
		System.out.println("上月同期="+getPrevMonthTodayString(pattern));
		System.out.println("上季同期="+getPrevSeasonTodayString(pattern));
		System.out.println("上年同期="+getPrevYearTodayString(pattern));
		System.out.println("上月末="+getPrevMonthLastDateString(pattern));
		System.out.println("上季末="+getPrevSeasonLastDateString(pattern));
		System.out.println("上年末="+getPrevYearLastDateString(pattern));
	}
	
	/**
	 * 判断给定的时间是否大于minute分钟
	 * @param date
	 * @param minute
	 * @return 大于5分钟返回true
	 * @throws ParseException 
	 */
	public static boolean localdateLtDate(String date, String minute) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1=sdf.parse(date);
		Date now=sdf.parse(sdf.format(new Date()));
		Integer min = Integer.parseInt(minute);
		if(now.getTime()-date1.getTime()>min*60*1000){
			return true;
		}
		else{
			return false;
		}
	}
	
}
