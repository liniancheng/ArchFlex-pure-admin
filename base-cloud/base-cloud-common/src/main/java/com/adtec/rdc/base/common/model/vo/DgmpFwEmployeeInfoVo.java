package com.adtec.rdc.base.common.model.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-23 13:26:34
 */
@Data
public class DgmpFwEmployeeInfoVo{

        /**
     * 员工ID
     */
    private String employeeId;
        /**
     * 员工工号
     */
    private String employeeNo;
        /**
     * 员工姓名
     */
    private String employeeName;
        /**
     * 员工性别
     */
    private String employeeSex;
        /**
     * 手机号码
     */
    private String employeeMobphone;
        /**
     * 部门编号
     */
    private String deptNo;
        /**
     * 出生日期
     */
    private String employeeBirthday;
        /**
     * 员工学历
     */
    private String employeeEducation;
        /**
     * 入职日期
     */
    private String employDate;
        /**
     * 员工状态
     */
    private String employeeStatus;
        /**
     * 离职日期
     */
    private String leaveDate;
        /**
     * 座机号码
     */
    private String employeeFixphone;
        /**
     * 应用ID
     */
    private String appId;
    /**
     * 员工外部邮箱
     */
    private String employeeEmail;
    /**
     * 员工内部邮箱
     */
    private String employeeEmailFst;
}
