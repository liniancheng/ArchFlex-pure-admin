package com.adtec.rdc.base.entrancevalidation.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.model.vo.SysDictItemVo;
import com.adtec.rdc.base.entrancevalidation.mapper.ChkVerifyConfigMapper;
import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyConfig;
import com.adtec.rdc.base.entrancevalidation.model.query.ChkVerifyConfigQuery;
import com.adtec.rdc.base.entrancevalidation.service.ChkVerifyConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ChkVerifyConfigServiceImpl
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 11:31:58
 **/
@Service
public class ChkVerifyConfigServiceImpl extends BaseServiceImpl<ChkVerifyConfigMapper, ChkVerifyConfig> implements ChkVerifyConfigService {

    @Autowired
    private ChkVerifyConfigMapper mapper;

    @Override
    public ChkVerifyConfigQuery pageByQuery(ChkVerifyConfigQuery query) {
        mapper.pageByQuery(query);
        return query;
    }

    @Override
    public Boolean saveChkVerifyConfig(ChkVerifyConfig chkVerifyConfig) {
        return this.save(chkVerifyConfig);
    }

    @Override
    public Boolean updateChkVerifyConfig(ChkVerifyConfig chkVerifyConfig) {
        return this.updateById(chkVerifyConfig);
    }

    @Override
    public ChkVerifyConfig getById(String ruleNo) {
        return mapper.selectById(ruleNo);
    }

    @Override
    public List<String> getSysNmList() {
        return mapper.selectSysNms();
    }

    @Override
    public List<String> getSysTabNmList(String sysNm) {
        return mapper.selectTabNmsBySysNm(sysNm);
    }

    @Override
    public List<String> getColumnNm(String sysTabNm) {
        return mapper.getColumnNm(sysTabNm);
    }

    @Override
    public Boolean updateVldFlg(List<String> ruleNos, String vldFlg) {
        for (String ruleNo : ruleNos) {
            ChkVerifyConfig chkVerifyConfig = mapper.selectById(ruleNo);

        }
        return mapper.updateVldFlg(ruleNos, vldFlg);
    }
}
