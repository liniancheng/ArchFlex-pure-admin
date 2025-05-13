package com.adtec.rdc.base.statistics.model.po;

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
 * 统计策略表
 * </p>
 *
 * @author hewei
 * @date 2020-01-15 17:47:49
 */
@Data
@Accessors(chain = true)
public class SysStatisticalStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 策略id
     */
    @TableId(value = "strategy_id", type = IdType.UUID)
    private String strategyId;

    /**
     * 服务地址
     */
    @TableField(value = "service_url", strategy = FieldStrategy.IGNORED) 
    private String serviceUrl;
    /**
     * 功能名称
     */
    @TableField(value = "function_name", strategy = FieldStrategy.IGNORED) 
    private String functionName;
    /**
     * 是否重复计数,0-否;1-是
     */
    @TableField(value = "repeat_flag", strategy = FieldStrategy.IGNORED) 
    private String repeatFlag;
    /**
     * 策略状态,0-停用;1-启用
     */
    @TableField(value = "config_status", strategy = FieldStrategy.IGNORED) 
    private String configStatus;
    /**
     * 删除标记
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED) 
    private String delFlag;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED) 
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED) 
    private Date modifyTime;

}