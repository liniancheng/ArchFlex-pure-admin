package com.littlelee.base.entrancevalidation.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.entrancevalidation.mapper.ChkVerifyInfoMapper;
import com.littlelee.base.entrancevalidation.model.po.ChkVerifyInfo;
import com.littlelee.base.entrancevalidation.model.query.ChkVerifyInfoQuery;
import com.littlelee.base.entrancevalidation.service.ChkVerifyInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ChkVerifyInfoServiceImpl
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 15:02:03
 **/
@Service
public class ChkVerifyInfoServiceImpl extends BaseServiceImpl<ChkVerifyInfoMapper, ChkVerifyInfo> implements ChkVerifyInfoService {

    @Autowired
    private ChkVerifyInfoMapper mapper;

    @Override
    public ChkVerifyInfoQuery pageByQuery(ChkVerifyInfoQuery query) {
        mapper.pageByQuery(query);
        return query;
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
    public List<String> getLgprList() {
        return null;
    }
}
