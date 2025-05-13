package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 机构信息表
 * </p>
 *
 * @author liushp
 * @date 2019-11-28 16:11:47
 */
@Data
@Accessors(chain = true)
public class SysBranchInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 机构号
     */
    @TableId(value = "branch_no", type = IdType.UUID) 
    private String branchNo;

    /**
     * 机构名称
     */
    @TableField(value = "branch_name", strategy = FieldStrategy.IGNORED) 
    private String branchName;
    /**
     * 机构简称
     */
    @TableField(value = "branch_short_name", strategy = FieldStrategy.IGNORED) 
    private String branchShortName;
    /**
     * 机构类型
     */
    @TableField(value = "branch_type", strategy = FieldStrategy.IGNORED) 
    private String branchType;
    /**
     * 父级机构编号
     */
    @TableField(value = "parent_branch_no", strategy = FieldStrategy.IGNORED) 
    private String parentBranchNo;
    /**
     * ETL同步
     * 0 - 是
     * 1 - 否
     */
    @TableField(value = "et_flag", strategy = FieldStrategy.IGNORED) 
    private String etFlag;
    /**
     * 是否是虚拟机构
     * 0 - 是
     * 1 - 否
     */
    @TableField(value = "vir_flag", strategy = FieldStrategy.IGNORED) 
    private String virFlag;
    /**
     * 虚拟字段，删除标识.0:正常；1：删除
     */
    @TableField(exist = false) 
    private String delFlag;

}