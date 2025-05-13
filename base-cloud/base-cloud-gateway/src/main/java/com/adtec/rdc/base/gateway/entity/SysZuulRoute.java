package com.adtec.rdc.base.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: JTao
 * @date: 2018/9/29 17:18
 * @description:
 */
@Data
public class SysZuulRoute implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * route Id
     */
    private String routeId;
    /**
     * 路由路径
     */
    private String routePath;
    /**
     * 服务名称
     */
    private String serviceId;
    /**
     * url代理
     */
    private String urlProxy;
    /**
     * 转发去掉前缀
     */
    private String stripPrefix;
    /**
     * 是否重试
     */
    private String retryableFlag;
    /**
     * 是否启用
     */
    private String enabledFlag;
    /**
     * 敏感请求头
     */
    private String headersList;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

}
