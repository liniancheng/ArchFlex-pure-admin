package com.adtec.rdc.base.enums;
/**
 * @author: dengchf     
 * @date:   2020年7月28日 
 * @Description:菜单相关枚举
 * @version V1.0 
 * @Copyright: adtec
 */
public enum MenuCodeEnum {
	TYPE_MENU("0"),
	TYPE_BUTTON("1"),
	
	AUTH_TRUR("1"),
	AUTH_FALSE("0"),
	
	HIDE_TRUR("1"),
	HIDE_FALSE("0"),
	
	ALIVE_TRUE("1"),
	ALIVE_FALSE("0"),
	
	;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private MenuCodeEnum(String code) {
		this.code = code;
	}
	
}
