package com.adtec.rdc.base.entrancevalidation.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyConfig;

import java.util.List;

/**
 * @ClassName ChkVerifyConfigMapper
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 10:39:45
 **/
public interface ChkVerifyConfigMapper extends BaseMapper<ChkVerifyConfig> {

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

    List<String> getColumnNm(String sysTabNm);

    Boolean updateVldFlg(List<String> ruleNos, String vldFlg);
}
