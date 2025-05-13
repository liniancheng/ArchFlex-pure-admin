package com.adtec.rdc.base.tenant.enums;

public enum AppFlagEnums {
	
	RUN("0", "业务端"),
	EXP("1","管理端");

	private String code;

	private String message;

	AppFlagEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
