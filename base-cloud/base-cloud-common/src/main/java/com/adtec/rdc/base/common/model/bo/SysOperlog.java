package com.adtec.rdc.base.common.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 *
 * @author: JTao
 * @date 2018-11-14 14:32:39
 */
@Data
@Accessors(chain = true)
public class SysOperlog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String logId;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 操作名
     */
    private String actionName;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String methodName;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private String operTime;

    /**
     * 异常信息
     */
    private String exceptionStr;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 操作状态 1 失败  0 成功
     */
    private String logStatus;

}