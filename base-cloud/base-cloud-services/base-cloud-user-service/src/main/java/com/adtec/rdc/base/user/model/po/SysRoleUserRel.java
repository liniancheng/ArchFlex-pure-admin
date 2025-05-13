package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
@Data
@Accessors(chain = true)
public class SysRoleUserRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String userId;

    /**
     * 主键
     */
    private String roleId;
}
