package com.adtec.rdc.base.layout.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色-布局权限表
 * </p>
 *
 * @author dengchf
 * @date 2020-08-25 15:06:33
 */
@Data
@Accessors(chain = true)
public class SysRoleLayoutRel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "ROLE_ID", type = IdType.UUID)
    private String roleId;

    /**
     * 布局ID
     */
    @TableField(value = "LAY_ID", strategy = FieldStrategy.IGNORED) 
    private String layId;

}