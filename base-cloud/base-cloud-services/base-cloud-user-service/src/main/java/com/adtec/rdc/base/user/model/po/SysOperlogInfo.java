package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class SysOperlogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.UUID)
    private String logId;

    /**
     * 日志类型
     */
    @TableField(value = "log_type", strategy = FieldStrategy.IGNORED)
    private String logType;

    /**
     * 模块名
     */
    @TableField(value = "module_name", strategy = FieldStrategy.IGNORED)
    private String moduleName;

    /**
     * 操作名
     */
    @TableField(value = "action_name", strategy = FieldStrategy.IGNORED)
    private String actionName;

    /**
     * 服务ID
     */
    @TableField(value = "service_id", strategy = FieldStrategy.IGNORED)
    private String serviceId;

    /**
     * 操作IP地址
     */
    @TableField(value = "remote_addr", strategy = FieldStrategy.IGNORED)
    private String remoteAddr;

    /**
     * 用户代理
     */
    @TableField(value = "user_agent", strategy = FieldStrategy.IGNORED)
    private String userAgent;

    /**
     * 请求URI
     */
    @TableField(value = "request_uri", strategy = FieldStrategy.IGNORED)
    private String requestUri;

    /**
     * 操作方式
     */
    @TableField(value = "method_name", strategy = FieldStrategy.IGNORED)
    private String methodName;

    /**
     * 操作提交的数据
     */
    @TableField(value = "params", strategy = FieldStrategy.IGNORED)
    private String params;

    /**
     * 执行时间
     */
    @TableField(value = "oper_time", strategy = FieldStrategy.IGNORED)
    private String operTime;

    /**
     * 异常信息
     */
    @TableField(value = "exception_str", strategy = FieldStrategy.IGNORED)
    private String exceptionStr;

    /**
     * 删除标记
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED)
    private String delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by", strategy = FieldStrategy.IGNORED)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", strategy = FieldStrategy.IGNORED)
    private Date updateTime;

    /**
     * 操作状态 1 失败  0 成功
     */
    @TableField(value = "log_status", strategy = FieldStrategy.IGNORED)
    private String logStatus;

}