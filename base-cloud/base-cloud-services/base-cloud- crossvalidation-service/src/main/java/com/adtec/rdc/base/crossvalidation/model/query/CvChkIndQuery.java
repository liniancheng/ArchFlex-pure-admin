package com.adtec.rdc.base.crossvalidation.model.query;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkInd;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
@Data
public class CvChkIndQuery extends Page<CvChkInd> {

        /**
     * 指标编号
     */
    private String indNo;
        /**
     * 指标名
     */
    private String indNm;
        /**
     * 指标等级
     */
    private String indLevel;
        /**
     * 父级指标编号
     */
    private String parentNo;
        /**
     * 触警级别
     */
    private String alarmLevel;
        /**
     * 指标系统
     */
    private String indSys;
        /**
     * 参考指标
     */
    private String busDirec;
        /**
     * 指标释义
     */
    private String indExpr;
        /**
     * 归口部门
     */
    private String deptNo;
        /**
     * 币种值
     */
    private String currencyVal;
        /**
     * 频度
     */
    private String dateVal;
        /**
     * 标志
     */
    private String remark;
        /**
     * 状态
     */
    private String state;
        /**
     * 创建时间
     */
    private String createTime;
        /**
     * 创建用户
     */
    private String createUser;
        /**
     * 更新时间
     */
    private String updateTime;
        /**
     * 更新用户
     */
    private String updateUser;
        /**
     * 菜单排序
     */
    private String menuOrder;

}
