package com.adtec.rdc.base.common.model.vo;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author: dengchf     
 * @date:   2020年3月5日 下午11:36:08   
 * @Description: 数据源实体
 * @version V1.0 
 * @Copyright: adtec
 */
@Data
public class SysDblinkVo {
	 /**
     * 数据源id
     */
    private String dblinkId;
    /**
     * 数据源英文名
     */
    private String dblinkName;
    /**
     * 数据源描述
     */
    private String dblinkRmk;
    /**
     * 数据源类型
     */
    private String dblinkType;
    /**
     * 驱动
     */
    private String dblinkDriver;
    /**
     * url
     */
    private String dblinkUrl;
    /**
     * 用户
     */
    private String dblinkUser;
    /**
     * 密码
     */
    private String dblinkPwd;
    /**
     * 连接池类型
     */
    private String poolType;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;
    /**
     * 初始化时物理连接个数
     */
    private Integer initialSize;
    /**
     * 最大连接池数量
     */
    private Integer maxActive;
    /**
     * 最小连接池数量
     */
    private Integer minIdle;
    /**
     * 缓存标识
     */
    private String cacheFlag;
    /**
     * 最大缓存数量
     */
    private Integer maxPsCatch;
    /**
     * 参数sql
     */
    private String validationQuery;
    /**
     * 获取连接等待超时时间
     */
    private Integer maxWait;
    /**
     * 取连接时是否检查连接有效性
     */
    private String testOnBorrow;
    /**
     * 归还连接时是否检查连接有效性
     */
    private String testOnReturn;
    /**
     * 获取连接时是否检查连接有效性
     */
    private String testWhileIdle;
    /**
     * 检测周期
     */
    private Integer timeBtnEvictionRunsMillis;
    /**
     * 最大空闲时间
     */
    private Integer minEvictableIdleTimeMillis;
    /**
     * PSCache打开标识
     */
    private String poolPreparedStatements;
    /**
     * pscatch缓存最大数
     */
    private Integer maxPsCatchSize;
    /**
     * 泄漏检查标识
     */
    private String removeAbandoned;
    /**
     * 超时时间
     */
    private Integer removeAbandonedTimeout;
    /**
     * 扩展插件
     */
    private String filters;
}
