package com.adtec.rdc.base.migrate.model.query;

import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@Data
public class SysMigrateInfoQuery extends Page<SysMigrateInfo> {

    /**
     * 主键字段
     */
    private String migColId;
    /**
     * 主键字段中文名
     */
    private String migColName;
    /**
     * 自关联结束标识
     */
    private String migEndSign;
    /**
     * id
     */
    private String migId;
    /**
     * 关联表关联字段
     */
    private String migRelId;
    /**
     * 关联类型：0：父->子;1:子->父；2：自关联
     */
    private String migRelType;
    /**
     * 关联表-关联字段解析类型;0/null:string;1:list.toString;2:map.toString;3:json;
     */
    private String migConverType;
    /**
     * 关联表-关联字段提取值
     */
    private String migRelValue;
    /**
     * 表中文名
     */
    private String migTabCname;
    /**
     * 表英文名
     */
    private String migTabName;
    /**
     * 表类型,0:目录；1：实体表；2：关系表；
     */
    private String migType;
    /**
     * 父级id
     */
    private String parentMigId;

}
