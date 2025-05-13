package com.adtec.rdc.base.common.util;

/**
 * 类名：UUID</br>
 * 模块：</br>
 * 说明：</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright ADTEC
 * @since 2019/11/20
 */
public class UUID {
    public static String generate() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}
