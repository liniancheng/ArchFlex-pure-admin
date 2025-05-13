package com.adtec.rdc.base.common.enums;

import lombok.Getter;

/**
 * @author: JTao
 * @date: 2018/12/14 14:53
 */
public enum  SmsTemplateEnum {

    /**
     * 腾讯云
     */
    LOGIN_CODE("涛哥学堂", "278850");

    @Getter
    String signName;

    @Getter
    String tempalte;

    private SmsTemplateEnum(String signName, String tempalte) {
        this.signName = signName;
        this.tempalte = tempalte;
    }
}
