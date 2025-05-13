package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色资源关联表
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
@Data
@Accessors(chain = true)
public class SysRoleMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String roleId;

    /**
     * 主键
     */
    private String menuId;

}
