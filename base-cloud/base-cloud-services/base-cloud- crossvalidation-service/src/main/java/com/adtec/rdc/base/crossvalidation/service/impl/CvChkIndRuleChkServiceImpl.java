package com.adtec.rdc.base.crossvalidation.service.impl;

import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.common.model.vo.SysDictItemVo;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkIndMapper;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkIndRuleChkMapper;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndRuleChkVo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkInd;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndRuleChkQuery;
import com.adtec.rdc.base.crossvalidation.model.query.CvRuleQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndRuleChkService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@Service
public class CvChkIndRuleChkServiceImpl extends BaseServiceImpl<CvChkIndRuleChkMapper, CvChkIndRuleChk> implements CvChkIndRuleChkService {
    @Autowired
    private CvChkIndRuleChkMapper mapper;
    @Autowired
    private CvChkIndMapper cvChkIndMapper;
    @Autowired
    private BaseUserService baseUserService;

    @Override
    public CvChkIndRuleChkQuery pageByQuery(CvChkIndRuleChkQuery query) {
        String ruleVal = query.getRuleVal();
        if (StringUtils.equals("00",ruleVal)){
            query.setRuleVal("");
        }
        mapper.pageByQuery(query);
        List<CvChkIndRuleChk> cvChkIndRuleChkList = query.getRecords();
        if (CollectionUtils.isNotEmpty(cvChkIndRuleChkList)) {
            //设置指标名称
            List<String> indNos = cvChkIndRuleChkList.stream().map(CvChkIndRuleChk::getIndNo).collect(Collectors.toList());
            List<CvChkInd> cvChkIndList = cvChkIndMapper.selectBatchIds(indNos);
            for (CvChkIndRuleChk cvChkIndRuleChk : cvChkIndRuleChkList) {
                for (CvChkInd cvChkInd : cvChkIndList) {
                    if (StringUtils.equals(cvChkIndRuleChk.getIndNo(), cvChkInd.getIndNo())) {
                        cvChkIndRuleChk.setIndNm(cvChkInd.getIndNm());
                    }
                }
            }
            //设置系统名称
            List<String> sysVals = cvChkIndRuleChkList.stream().map(CvChkIndRuleChk::getSysVal).collect(Collectors.toList());
            List<SysDictItemVo> sysDictItemList = baseUserService.selectList(sysVals);
            for (CvChkIndRuleChk cvChkIndRuleChk : cvChkIndRuleChkList) {
                for (SysDictItemVo sysDictItem : sysDictItemList) {
                    if (StringUtils.equals(cvChkIndRuleChk.getSysVal(), sysDictItem.getItemValue())) {
                        cvChkIndRuleChk.setSysNm(sysDictItem.getItemText());
                    }
                }
            }
            //设置法人名称
            List<String> orgVals = cvChkIndRuleChkList.stream().map(CvChkIndRuleChk::getOrgVal).collect(Collectors.toList());
            List<SysBranchInfoVo> sysBranchInfoList = baseUserService.selectBatchByIds(orgVals);
            for (CvChkIndRuleChk cvChkIndRuleChk : cvChkIndRuleChkList) {
                for (SysBranchInfoVo sysBranchInfo : sysBranchInfoList) {
                    if (StringUtils.equals(cvChkIndRuleChk.getOrgVal(), sysBranchInfo.getBranchNo())) {
                        cvChkIndRuleChk.setOrgNm(sysBranchInfo.getBranchName());
                    }
                }
            }
        }
        query.setRecords(cvChkIndRuleChkList);
        return query;
    }

    @Override
    public Boolean saveCvChkIndRuleChk(CvChkIndRuleChk cvindrule) {
        String sysVal = cvindrule.getSysVal();
        if (StringUtils.equals(sysVal, "R1104")) {
            cvindrule.setT1104Frq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "EAST3")) {
            cvindrule.setEastFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "KHFX")) {
            cvindrule.setCustRskFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "DWDK")) {
            cvindrule.setFinBaseDataFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "JRTJ")) {
            cvindrule.setBigFocusFrq(cvindrule.getDateVal());
        }
        String orgVal = cvindrule.getOrgVal();
        if (StringUtils.isNotBlank(orgVal)){
            cvindrule.setRuleVal("02");
        }
        return this.save(cvindrule);
    }

    @Override
    public Boolean updateCvChkIndRuleChk(CvChkIndRuleChk cvindrule) {
        String sysVal = cvindrule.getSysVal();
        if (StringUtils.equals(sysVal, "R1104")) {
            cvindrule.setT1104Frq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "EAST3")) {
            cvindrule.setEastFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "KHFX")) {
            cvindrule.setCustRskFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "DWDK")) {
            cvindrule.setFinBaseDataFrq(cvindrule.getDateVal());
        }
        if (StringUtils.equals(sysVal, "JRTJ")) {
            cvindrule.setBigFocusFrq(cvindrule.getDateVal());
        }
        return this.updateById(cvindrule);
    }

    @Override
    public CvChkIndRuleChk getById(String id) {
        CvChkIndRuleChk cvindrule = mapper.selectById(id);
        String indNo = cvindrule.getIndNo();
        CvChkInd cvChkInd = cvChkIndMapper.selectById(indNo);
        cvindrule.setIndNm(cvChkInd.getIndNm());
        String sysVal = cvindrule.getSysVal();
        if (StringUtils.equals(sysVal, "R1104")) {
            cvindrule.setSysNm("1104");
            cvindrule.setDateVal(cvindrule.getT1104Frq());
        }
        if (StringUtils.equals(sysVal, "EAST3")) {
            cvindrule.setSysNm("EAST报表");
            cvindrule.setDateVal(cvindrule.getEastFrq());
        }
        if (StringUtils.equals(sysVal, "KHFX")) {
            cvindrule.setSysNm("客户风险");
            cvindrule.setDateVal(cvindrule.getCustRskFrq());
        }
        if (StringUtils.equals(sysVal, "DWDK")) {
            cvindrule.setSysNm("金融基础数据管理系统");
            cvindrule.setDateVal(cvindrule.getFinBaseDataFrq());
        }
        if (StringUtils.equals(sysVal, "JRTJ")) {
            cvindrule.setSysNm("金融统计大集中");
            cvindrule.setDateVal(cvindrule.getBigFocusFrq());
        }
        return cvindrule;
    }

    @Override
    public CvRuleQuery rulePageByQuery(CvRuleQuery query) {
        if (CollectionUtils.isEmpty(query.getIndNos())){
            query.setIndNos(null);
        }
        if (CollectionUtils.isEmpty(query.getSysVals())){
            query.setSysVals(null);
        }
        String ruleVal = query.getRuleVal();
        if (StringUtils.equals("00",ruleVal)){
            query.setRuleVal("");
        }
        mapper.rulePageByQuery(query);
        List<CvChkIndRuleChkVo> chkIndRuleChkVos = query.getRecords();
        if (CollectionUtils.isNotEmpty(chkIndRuleChkVos)) {
            for (CvChkIndRuleChkVo chkIndRuleChkVo : chkIndRuleChkVos) {
                String sysVal = chkIndRuleChkVo.getSysVal();
                if (StringUtils.equals(sysVal, "R1104")) {
                    chkIndRuleChkVo.setSysNm("1104");
                }
                if (StringUtils.equals(sysVal, "EAST3")) {
                    chkIndRuleChkVo.setSysNm("EAST报表");
                }
                if (StringUtils.equals(sysVal, "KHFX")) {
                    chkIndRuleChkVo.setSysNm("客户风险");
                }
                if (StringUtils.equals(sysVal, "DWDK")) {
                    chkIndRuleChkVo.setSysNm("金融基础数据管理系统");
                }
                if (StringUtils.equals(sysVal, "JRTJ")) {
                    chkIndRuleChkVo.setSysNm("金融统计大集中");
                }
            }
            //设置法人名称
            List<String> orgVals = chkIndRuleChkVos.stream().map(CvChkIndRuleChkVo::getOrgVal).collect(Collectors.toList());
            List<SysBranchInfoVo> sysBranchInfoList = baseUserService.selectBatchByIds(orgVals);
            for (CvChkIndRuleChkVo chkIndRuleChkVo : chkIndRuleChkVos) {
                for (SysBranchInfoVo sysBranchInfo : sysBranchInfoList) {
                    if (StringUtils.equals(chkIndRuleChkVo.getOrgVal(), sysBranchInfo.getBranchNo())) {
                        chkIndRuleChkVo.setOrgNm(sysBranchInfo.getBranchName());
                    }
                }
            }
        }
        query.setRecords(chkIndRuleChkVos);
        return query;
    }
}
