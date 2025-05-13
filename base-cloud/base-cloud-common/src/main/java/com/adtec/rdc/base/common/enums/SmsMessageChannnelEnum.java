package com.adtec.rdc.base.common.enums;

import lombok.Getter;

/**
 * @author: JTao
 * @date: 2018/12/3 10:44
 */

public enum  SmsMessageChannnelEnum {

    /**
     * 腾讯云
     */
    TENCENT_CLOUD("smsTencentCloudMessageHandler", "腾讯云");

    @Getter
    String code;

    @Getter
    String desc;

    private SmsMessageChannnelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
