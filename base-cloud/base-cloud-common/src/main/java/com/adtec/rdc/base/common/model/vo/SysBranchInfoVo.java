package com.adtec.rdc.base.common.model.vo;


import lombok.Data;


import java.io.Serializable;

/**
 * <p>
 * 机构信息表
 * </p>
 *
 * @author liushp
 * @date 2019-11-28 16:11:47
 */
@Data
public class SysBranchInfoVo implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 机构号
     */
    private String branchNo;

    /**
     * 机构名称
     */
    private String branchName;
    /**
     * 机构简称
     */
    private String branchShortName;
    /**
     * 机构类型
     */
    private String branchType;
    /**
     * 父级机构编号
     */
    private String parentBranchNo;
    /**
     * ETL同步
     * 0 - 是
     * 1 - 否
     */
    private String etFlag;
    /**
     * 是否是虚拟机构
     * 0 - 是
     * 1 - 否
     */
    private String virFlag;
    /**
     * 虚拟字段，删除标识.0:正常；1：删除
     */
    private String delFlag;

}
