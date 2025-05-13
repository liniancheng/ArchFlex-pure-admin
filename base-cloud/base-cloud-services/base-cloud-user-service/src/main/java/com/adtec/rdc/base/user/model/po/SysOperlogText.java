package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志表
 * </p>
 * 
 * @author: JTao
 * @date 2018-11-14 14:32:39
 */
@Data
@Accessors(chain = true)
public class SysOperlogText implements Serializable {
    private static final long serialVersionUID = 1L;
    private String logId;
    private String logName;
    private String logPath;
    private BigDecimal logSize;
    private String logTxt;

}