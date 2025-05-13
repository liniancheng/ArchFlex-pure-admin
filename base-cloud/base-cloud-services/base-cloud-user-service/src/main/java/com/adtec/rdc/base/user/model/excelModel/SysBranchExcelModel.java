package com.adtec.rdc.base.user.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;

/**
 * <p>
 * 机构信息表
 * </p>
 *
 * @author liushp
 * @date 2019-11-28 16:11:47
 */
@Data
public class SysBranchExcelModel extends BaseRowModel {

    @ExcelProperty(value="机构号",index=0)
    private String branchNo;
    @ExcelProperty(value="机构名称",index=1)
    private String branchName;
    @ExcelProperty(value="机构简称",index=2)
    private String branchShortName;
    @ExcelProperty(value="机构类型",index=3)
    private String branchType;
    @ExcelProperty(value="父级机构编号",index=4)
    private String parentBranchNo;
    @ExcelProperty(value="是否是虚拟机构",index=5)
    private String virFlag;

}