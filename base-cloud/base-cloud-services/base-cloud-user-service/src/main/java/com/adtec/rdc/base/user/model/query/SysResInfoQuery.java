package com.adtec.rdc.base.user.model.query;


import com.adtec.rdc.base.user.model.po.SysResInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-29 14:14:04
 */
@Data
public class SysResInfoQuery extends Page<SysResInfo> {

        /**
     * id
     */
    private String id;
        /**
     * 数据源类型(1:mysql   2:db2   3:orcle)
     */
    private String dbType;

}
