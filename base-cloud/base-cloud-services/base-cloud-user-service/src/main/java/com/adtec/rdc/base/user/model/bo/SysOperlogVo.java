package com.adtec.rdc.base.user.model.bo;

import java.util.Date;

import com.adtec.rdc.base.user.model.po.SysOperlogInfo;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysOperlogVo {
	/**
     * 主键
     */
	@ExcelProperty("主键")
    private String logId;

    /**
     * 日志类型
     */
	@ExcelProperty("日志类型")
    private String logType;

    /**
     * 模块名
     */
	@ExcelProperty("模块名")
    private String moduleName;

    /**
     * 操作名
     */
	@ExcelProperty("操作名")
    private String actionName;

    /**
     * 服务ID
     */
	@ExcelProperty("服务ID")
    private String serviceId;

    /**
     * 操作IP地址
     */
	@ExcelProperty("操作IP地址")
    private String remoteAddr;

    /**
     * 用户代理
     */
	@ExcelProperty("用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
	@ExcelProperty("请求URI")
    private String requestUri;

    /**
     * 操作方式
     */
	@ExcelProperty("操作方式")
    private String methodName;

    /**
     * 操作提交的数据
     */
	@ExcelProperty("操作提交的数据")
    private String params;

    /**
     * 执行时间
     */
	@ExcelProperty("执行时间")
    private String operTime;

    /**
     * 异常信息
     */
	@ExcelProperty("异常信息")
    private String exceptionStr;

    /**
     * 删除标记
     */
	@ExcelProperty("删除标记")
    private String delFlag;

    /**
     * 创建者
     */
	@ExcelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
	@ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
	@ExcelProperty("更新时间")
    private Date updateTime;

    /**
     * 操作状态 1 失败  0 成功
     */
	@ExcelProperty("操作状态 1 失败  0 成功")
    private String logStatus;

}
