package com.adtec.rdc.base.layout.model.query;

import com.adtec.rdc.base.layout.model.po.SysRoleLayoutItemRel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2020-08-25 15:05:27
 */
@Data
public class SysRoleLayoutItemRelQuery extends Page<SysRoleLayoutItemRel> {

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 数据项ID
     */
    private String itemId;

}
