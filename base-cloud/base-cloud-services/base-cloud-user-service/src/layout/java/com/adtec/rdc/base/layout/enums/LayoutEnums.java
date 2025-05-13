package com.adtec.rdc.base.layout.enums;

public enum LayoutEnums {
	/** 系统默认布局 */
	TYPE_DEFAULT("0"),
	/** 系统设置布局 */
	TYPE_SYSTEM("1"),
	/** 用户自定义布局 */
	TYPE_CUSTOM("2")
	;
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private LayoutEnums(String code) {
		this.code = code;
	}
	
	

}
