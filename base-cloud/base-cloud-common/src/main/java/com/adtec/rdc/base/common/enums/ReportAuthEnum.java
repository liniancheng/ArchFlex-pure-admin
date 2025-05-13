package com.adtec.rdc.base.common.enums;

public enum ReportAuthEnum {
	//类型
	TYPE_ROLE_USER_REL("0"),
	TYPE_USER_ROLE_REL("1"),
	//操作类型
	OPER_TYPE_ADD("0"),
	OPER_TYPE_UPDATE("1"),
	OPER_TYPE_DELETE("2"),
	;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private ReportAuthEnum(String type) {
		this.type = type;
	}
	
}
