package com.littlelee.base.entrancevalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.entrancevalidation.model.po.ChkVerifyConfig;
import com.littlelee.base.entrancevalidation.model.query.ChkVerifyConfigQuery;

import java.util.List;

/**
 * @ClassName ChkVerifyConfigService
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 10:47:26
 **/
public interface ChkVerifyConfigService extends BaseService<ChkVerifyConfig> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    ChkVerifyConfigQuery pageByQuery(ChkVerifyConfigQuery query);

    /**
     * 添加规则
     * @param chkVerifyConfig
     * @return
     */
    Boolean saveChkVerifyConfig(ChkVerifyConfig chkVerifyConfig);

    /**
     * 修改规则
     * @param chkVerifyConfig
     * @return
     */
    Boolean updateChkVerifyConfig(ChkVerifyConfig chkVerifyConfig);

    /**
     * 通过规则编号获取规则
     * @param ruleNo
     * @return
     */
    ChkVerifyConfig getById(String ruleNo);

    /**
     * 获取系统名
     * @return
     */
    List<String> getSysNmList();

    /**
     * 获取表名
     */
    List<String> getSysTabNmList(String sysNm);

    /**
     * 获取列名
     * @param sysTabNm
     * @return
     */
    List<String> getColumnNm(String sysTabNm);

    /**
     * 启用/禁用
     * @param ruleNo
     * @return
     */
    Boolean updateVldFlg(List<String> ruleNo, String vldFlg);
}
