package com.adtec.rdc.base.common.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 类名：StatisticalStrategy</br>
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
public class StatisticalStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

    private String strategyId;

    private String serviceUrl;
}
