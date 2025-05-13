package com.adtec.rdc.base.entrancevalidation.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyInfo;

import java.util.List;

/**
 * @ClassName ChkVerifyInfoMapper
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 14:59:58
 **/
public interface ChkVerifyInfoMapper extends BaseMapper<ChkVerifyInfo> {

    /**
     * 查询系统名
     * @return
     */
    List<String> selectSysNms();

    /**
     * 通过系统名查询表名
     * @param sysNm
     * @return
     */
    List<String> selectTabNmsBySysNm(String sysNm);

    /**
     * 获取法人
     */
    List<String> getLgprList();
}
