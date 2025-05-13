package com.adtec.rdc.base.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	private static Pattern pattern;
	private static final String MAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	
	/**
	 * 判断某字符串是否与正则表达式匹配
	 * @param regex 正则表达式
	 * @param input 输入的字符串
	 * @return true | false
	 */
	public static boolean matching(String regex, String input){
		boolean flag = false;
		pattern = Pattern.compile(regex);
		if(pattern.matcher(input).find()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 逗号分割字符串
	 * {},[],()中的逗号不做分割
	 * 不支持嵌套括号
	 * @param express
	 * @return
	 */
	public static List<String> splitByComma(String express){
		List<String> result = new ArrayList<String>();
		if(express != null && express.length()>0){
			String regex = "\\{[^}]*\\}|\\[[^]]*\\]|\\([^)]*\\)";
			pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(express);
			int i = 0;
			Map<String, String> map = new HashMap<String, String>();
			while(matcher.find()){
				String key = "@" + i + "@";
				String value = matcher.group();
				map.put(key, value);
				int index = express.indexOf(value);
				express = express.substring(0, index) + key + express.substring(index+value.length(), express.length());
				i++;
			}
			String[] ss = express.split(",");
			for(String key : map.keySet()){
				for(int ii=0,j=ss.length; ii<j; ii++){
					if(ss[ii].indexOf(key)>=0){
						ss[ii] = ss[ii].replace(key, map.get(key));
					}
				}
			}
			result = Arrays.asList(ss);
		}
		return result;
	}
	
	public static boolean validateMail(String mail){
		return matching(MAIL_REGEX, mail);
	}
	
	public static void main(String[] args) {
		String input = "11.com";
		System.out.println(validateMail(input));
	}
}
