package com.adtec.rdc.base.entrancevalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyInfo;
import com.adtec.rdc.base.entrancevalidation.model.query.ChkVerifyInfoQuery;

import java.util.List;

/**
 * @ClassName ChkVerifyInfoService
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 15:01:11
 **/
public interface ChkVerifyInfoService extends BaseService<ChkVerifyInfo> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    ChkVerifyInfoQuery pageByQuery(ChkVerifyInfoQuery query);

    /**
     * 获取系统名
     * @return
     */
    List<String> getSysNmList();

    /**
     * 获取表名
     * @param sysNm
     * @return
     */
    List<String> getSysTabNmList(String sysNm);

    /**
     * 获取法人
     * @return
     */
    List<String> getLgprList();
}
