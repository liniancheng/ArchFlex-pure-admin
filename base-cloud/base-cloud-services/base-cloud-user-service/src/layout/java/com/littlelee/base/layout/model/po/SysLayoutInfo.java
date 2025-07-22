package com.littlelee.base.layout.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 布局信息表
 * </p>
 *
 * @author littlelee
 * @date 2020-08-25 15:04:09
 */
@Data
@Accessors(chain = true)
public class SysLayoutInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "LAY_ID", type = IdType.ASSIGN_UUID)
    private String layId;

    /**
     * 布局名称
     */
    @TableField(value = "LAY_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String layName;
    /**
     * 应用ID
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String appId;
    /**
     * 布局类型，0-默认布局；1-权限布局；2-自定义布局
     */
    @TableField(value = "LAY_TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String layType;
    /**
     * 应用ID
     */
    @TableField(value = "LAY_LEVEL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Integer layLevel;
    /**
     * 登录名
     */
    @TableField(value = "LOGIN_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String loginName;
    /**
     * 描述
     */
    @TableField(value = "LAY_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String layRmk;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;
    
    /**
     * 数据项列表
     */
    @TableField(exist = false)
    private List<SysLayoutItemInfo> listItems;

}