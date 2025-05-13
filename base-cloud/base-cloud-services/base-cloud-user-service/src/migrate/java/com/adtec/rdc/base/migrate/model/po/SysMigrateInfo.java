package com.adtec.rdc.base.migrate.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 导入导出信息配置表
 * </p>
 *
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@Data
@Accessors(chain = true)
public class SysMigrateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "mig_id", type = IdType.UUID)
    private String migId;

    /**
     * 主键字段
     */
    @TableField(value = "mig_col_id", strategy = FieldStrategy.IGNORED) 
    private String migColId;
    /**
     * 主键字段中文名
     */
    @TableField(value = "mig_col_name", strategy = FieldStrategy.IGNORED) 
    private String migColName;
    /**
     * 自关联结束标识
     */
    @TableField(value = "mig_end_sign", strategy = FieldStrategy.IGNORED) 
    private String migEndSign;
    /**
     * 关联表关联字段
     */
    @TableField(value = "mig_rel_id", strategy = FieldStrategy.IGNORED) 
    private String migRelId;
    /**
     * 关联类型：0：父->子;1:子->父；2：自关联
     */
    @TableField(value = "mig_rel_type", strategy = FieldStrategy.IGNORED) 
    private String migRelType;
    /**
     * 关联表-关联字段解析类型;0/null:string;1:list.toString;2:map.toString;3:json;
     */
    @TableField(value = "mig_conver_type", strategy = FieldStrategy.IGNORED) 
    private String migConverType;
    /**
     * 关联表-关联字段提取值
     */
    @TableField(value = "mig_rel_value", strategy = FieldStrategy.IGNORED) 
    private String migRelValue;
    /**
     * 表中文名
     */
    @TableField(value = "mig_tab_cname", strategy = FieldStrategy.IGNORED) 
    private String migTabCname;
    /**
     * 表英文名
     */
    @TableField(value = "mig_tab_name", strategy = FieldStrategy.IGNORED) 
    private String migTabName;
    /**
     * 表类型,0:目录；1：实体表；2：关系表；
     */
    @TableField(value = "mig_type", strategy = FieldStrategy.IGNORED) 
    private String migType;
    /**
     * 父级id
     */
    @TableField(value = "parent_mig_id", strategy = FieldStrategy.IGNORED) 
    private String parentMigId;

}