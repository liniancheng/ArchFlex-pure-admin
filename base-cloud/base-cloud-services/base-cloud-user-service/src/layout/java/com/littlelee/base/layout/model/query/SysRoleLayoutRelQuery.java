package com.littlelee.base.layout.model.query;

import com.littlelee.base.layout.model.po.SysRoleLayoutRel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author littlelee
 * @date 2020-08-25 15:06:33
 */
@Data
public class SysRoleLayoutRelQuery extends Page<SysRoleLayoutRel> {

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 布局ID
     */
    private String layId;

}
