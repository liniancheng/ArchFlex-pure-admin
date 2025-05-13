package com.adtec.rdc.base.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 数据源信息表
 * </p>
 *
 * @author dengchf
 * @date 2020-03-07 12:14:44
 */
@Data
@Accessors(chain = true)
public class SysDblinkInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据源id
     */
    @TableId(value = "dblink_id", type = IdType.UUID)
    private String dblinkId;

    /**
     * 数据源英文名
     */
    @TableField(value = "dblink_name", strategy = FieldStrategy.IGNORED) 
    private String dblinkName;
    /**
     * 数据源描述
     */
    @TableField(value = "dblink_rmk", strategy = FieldStrategy.IGNORED) 
    private String dblinkRmk;
    /**
     * 数据源类型
     */
    @TableField(value = "dblink_type", strategy = FieldStrategy.IGNORED) 
    private String dblinkType;
    /**
     * 驱动
     */
    @TableField(value = "dblink_driver", strategy = FieldStrategy.IGNORED) 
    private String dblinkDriver;
    /**
     * url
     */
    @TableField(value = "dblink_url", strategy = FieldStrategy.IGNORED) 
    private String dblinkUrl;
    /**
     * 用户
     */
    @TableField(value = "dblink_user", strategy = FieldStrategy.IGNORED) 
    private String dblinkUser;
    /**
     * 密码
     */
    @TableField(value = "dblink_pwd", strategy = FieldStrategy.IGNORED) 
    private String dblinkPwd;
    /**
     * 连接池类型
     */
    @TableField(value = "pool_type", strategy = FieldStrategy.IGNORED) 
    private String poolType;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT) 
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED, fill = FieldFill.UPDATE) 
    private LocalDateTime modifyTime;
    /**
     * 初始化时物理连接个数
     */
    @TableField(value = "initial_size", strategy = FieldStrategy.IGNORED) 
    private Integer initialSize;
    /**
     * 最大连接池数量
     */
    @TableField(value = "max_active", strategy = FieldStrategy.IGNORED) 
    private Integer maxActive;
    /**
     * 最小连接池数量
     */
    @TableField(value = "min_idle", strategy = FieldStrategy.IGNORED) 
    private Integer minIdle;
    /**
     * 缓存标识
     */
    @TableField(value = "cache_flag", strategy = FieldStrategy.IGNORED) 
    private String cacheFlag;
    /**
     * 最大缓存数量
     */
    @TableField(value = "max_ps_catch", strategy = FieldStrategy.IGNORED) 
    private Integer maxPsCatch;
    /**
     * 参数sql
     */
    @TableField(value = "validation_query", strategy = FieldStrategy.IGNORED) 
    private String validationQuery;
    /**
     * 获取连接等待超时时间
     */
    @TableField(value = "max_wait", strategy = FieldStrategy.IGNORED) 
    private Integer maxWait;
    /**
     * 取连接时是否检查连接有效性
     */
    @TableField(value = "test_on_borrow", strategy = FieldStrategy.IGNORED) 
    private String testOnBorrow;
    /**
     * 归还连接时是否检查连接有效性
     */
    @TableField(value = "test_on_return", strategy = FieldStrategy.IGNORED) 
    private String testOnReturn;
    /**
     * 获取连接时是否检查连接有效性
     */
    @TableField(value = "test_while_idle", strategy = FieldStrategy.IGNORED) 
    private String testWhileIdle;
    /**
     * 检测周期
     */
    @TableField(value = "time_btn_eviction_runs_millis", strategy = FieldStrategy.IGNORED) 
    private Integer timeBtnEvictionRunsMillis;
    /**
     * 最大空闲时间
     */
    @TableField(value = "min_evictable_idle_time_millis", strategy = FieldStrategy.IGNORED) 
    private Integer minEvictableIdleTimeMillis;
    /**
     * PSCache打开标识
     */
    @TableField(value = "pool_prepared_statements", strategy = FieldStrategy.IGNORED) 
    private String poolPreparedStatements;
    /**
     * pscatch缓存最大数
     */
    @TableField(value = "max_ps_catch_size", strategy = FieldStrategy.IGNORED) 
    private Integer maxPsCatchSize;
    /**
     * 泄漏检查标识
     */
    @TableField(value = "remove_abandoned", strategy = FieldStrategy.IGNORED) 
    private String removeAbandoned;
    /**
     * 超时时间
     */
    @TableField(value = "remove_abandoned_timeout", strategy = FieldStrategy.IGNORED) 
    private Integer removeAbandonedTimeout;
    /**
     * 扩展插件
     */
    @TableField(value = "filters", strategy = FieldStrategy.IGNORED) 
    private String filters;

}