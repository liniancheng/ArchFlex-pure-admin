package com.adtec.rdc.base.common.enums;

/**
 * @author: JTao
 * @date: 2018/10/12 10:51
 * @description: 响应信息code枚举
 */
public enum  ResponseCodeEnum {

    SUCCESS(0, "操作成功"),

    NOT_LOGIN(-1, "需要登录"),

    FAIL(-1, "操作失败"),

    PERMISSION_DEFINED(2, "元权限操作");


    private Integer code;

    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
