package com.adtec.rdc.base.common.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 类名：StatisticalInfo</br>
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
 * @since 2020/1/17
 */
@Data
@Accessors(chain = true)
public class StatisticalInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String statisticalId;

    private String strategyId;

    private String requestUrl;

    private String requestUserId;

    private String requestHost;

    private String userAgent;

    private String requestMethod;

    private String requestTime;

    private String responseStatus;

    private int responseDataSize;
}
