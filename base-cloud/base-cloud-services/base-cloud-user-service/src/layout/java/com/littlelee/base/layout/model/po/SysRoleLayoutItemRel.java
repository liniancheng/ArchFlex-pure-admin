package com.littlelee.base.layout.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色-数据项权限表
 * </p>
 *
 * @author littlelee
 * @date 2020-08-25 15:05:27
 */
@Data
@Accessors(chain = true)
public class SysRoleLayoutItemRel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "ROLE_ID", type = IdType.ASSIGN_UUID)
    private String roleId;

    /**
     * 数据项ID
     */
    @TableField(value = "ITEM_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String itemId;

}