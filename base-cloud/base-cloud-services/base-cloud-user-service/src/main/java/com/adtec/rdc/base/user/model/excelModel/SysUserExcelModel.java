package com.adtec.rdc.base.user.model.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;

@Data
public class SysUserExcelModel extends BaseRowModel {
    @ExcelProperty(value="登录名",index=0)
    private String loginName;
    @ExcelProperty(value="用户名",index=1)
    private String userName;
    @ExcelProperty(value="邮箱",index=2)
    private String userEmail;
    @ExcelProperty(value="手机号",index=3)
    private String userMobTel;
    @ExcelProperty(value="角色",index=4)
    private String roleNames;
    @ExcelProperty(value="机构",index=5)
    private String branchName;
    @ExcelProperty(value="状态",index=6)
    private String statusName;
}
