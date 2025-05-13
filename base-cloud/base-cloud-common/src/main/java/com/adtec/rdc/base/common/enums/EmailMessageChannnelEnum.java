package com.adtec.rdc.base.common.enums;

import lombok.Getter;

public enum EmailMessageChannnelEnum {
	
	/**
     * JavaMail
     */
    JAVA_MAIL("emailSpringJavaMailMessageHandler", "JavaMail");

    @Getter
    String code;

    @Getter
    String desc;

    private EmailMessageChannnelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
