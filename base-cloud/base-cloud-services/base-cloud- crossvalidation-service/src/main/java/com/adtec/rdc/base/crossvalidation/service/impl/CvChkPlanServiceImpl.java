package com.adtec.rdc.base.crossvalidation.service.impl;

import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysDictItemVo;
import com.adtec.rdc.base.crossvalidation.mapper.*;
import com.adtec.rdc.base.crossvalidation.model.po.*;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanDetailService;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanResultService;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author adtec
 * @date
 */
@Service
public class CvChkPlanServiceImpl extends BaseServiceImpl<CvChkPlanMapper, CvChkPlan> implements CvChkPlanService {
    @Autowired
    private CvChkPlanMapper mapper;
    @Autowired
    private CvChkPlanDetailService detailService;
    @Autowired
    private CvChkPlanResultService resultService;
    @Autowired
    private BaseUserService dictItemService;
    @Autowired
    private CvChkIndRuleChkMapper ruleChkMapper;
    /**
     * 计划状态 1.未完成2.已完成
     */
    private static final String PLAN_NOT_RUN = "1";

    @Override
    public CvChkPlanQuery pageByQuery(CvChkPlanQuery query) {
        List<String> sysValList = query.getSysVals();
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(sysValList)) {
            for (int i = 0; i < sysValList.size(); i++) {
                builder.append(sysValList.get(i));
                if (i == sysValList.size() - 1) {
                    break;
                }
                builder.append(",");
            }
        }
        String sysList = builder.toString();
        query.setSysVal(sysList);
        query.setDesc("CREATE_TIME");
        mapper.pageByQuery(query);
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> saveCvChkPlan(CvChkPlan cvchkplan) {
        if (cvchkplan == null || StringUtils.isBlank(cvchkplan.getOrgVal())){
            throw new ServiceException("创建校验计划请求参数有误,请重新创建!");
        }
        Map<String, String> map = new HashMap<>();
        cvchkplan.setPlanState(PLAN_NOT_RUN);
        if (CollectionUtils.isEmpty(cvchkplan.getIndNos())) {
            cvchkplan.setIndList("all");
        }else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cvchkplan.getIndNos().size(); i++) {
                builder.append(cvchkplan.getIndNos().get(i));
                if (i == cvchkplan.getIndNos().size() - 1){
                    break;
                }
                builder.append(",");
            }
            cvchkplan.setIndList(builder.toString());
        }
        if (CollectionUtils.isEmpty(cvchkplan.getSysVals())){
            cvchkplan.setSysList(this.getSysList());
        }else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cvchkplan.getSysVals().size(); i++) {
                builder.append(cvchkplan.getSysVals().get(i));
                if (i == cvchkplan.getSysVals().size() - 1){
                    break;
                }
                builder.append(",");
            }
            cvchkplan.setSysList(builder.toString());
        }
        boolean save = this.save(cvchkplan);
        if (!save){
            throw new ServiceException("校验计划创建失败,请重新创建!");
        }
        //计划创建完成,进行计划详情存储
        Boolean saveCvChkPlanDetail = saveCvChkPlanDetail(cvchkplan);
        if (!saveCvChkPlanDetail){
            throw new ServiceException("校验计划详情创建失败,请重新创建校验计划!");
        }
        map.put("planId", cvchkplan.getId());
        map.put("dataTime", cvchkplan.getDataTime());
        return map;
    }

    private Boolean saveCvChkPlanDetail(CvChkPlan cvChkPlan) {
        List<CvChkPlanDetail> cvChkPlanDetailList = new ArrayList<>();
        List<CvChkIndRuleChk> cvChkIndRuleChkList = null;
        String planId = cvChkPlan.getId();
        String dataTime = cvChkPlan.getDataTime();
        String indList = cvChkPlan.getIndList();
        String sysList = cvChkPlan.getSysList();
        String orgVal = cvChkPlan.getOrgVal();
        if (StringUtils.equals("all",indList) && sysList.split(",").length == 5){
            QueryWrapper<CvChkIndRuleChk> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(CvChkIndRuleChk::getOrgVal,orgVal);
            cvChkIndRuleChkList = ruleChkMapper.selectList(queryWrapper);
        }else {
            QueryWrapper<CvChkIndRuleChk> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(CvChkIndRuleChk::getOrgVal,orgVal)
                    .in(CvChkIndRuleChk::getIndNo,Arrays.asList(indList.split(",")))
                    .in(CvChkIndRuleChk::getSysVal,Arrays.asList(sysList.split(",")));
            cvChkIndRuleChkList = ruleChkMapper.selectList(queryWrapper);
        }
        if (CollectionUtils.isEmpty(cvChkIndRuleChkList)) {
            return false;
        }
        for (CvChkIndRuleChk cvChkIndRuleChk : cvChkIndRuleChkList) {
            String sysVal = cvChkIndRuleChk.getSysVal();
            CvChkPlanDetail cvChkPlanDetail = new CvChkPlanDetail();
            cvChkPlanDetail.setPlanId(planId);
            cvChkPlanDetail.setIndNo(cvChkIndRuleChk.getIndNo());
            cvChkPlanDetail.setOrgVal(cvChkIndRuleChk.getOrgVal());
            cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getDateVal());
            if (StringUtils.equals(sysVal, "R1104")) {
                cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getT1104Frq());
            }
            if (StringUtils.equals(sysVal, "EAST3")) {
                cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getEastFrq());
            }
            if (StringUtils.equals(sysVal, "KHFX")) {
                cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getCustRskFrq());
            }
            if (StringUtils.equals(sysVal, "DWDK")) {
                cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getFinBaseDataFrq());
            }
            if (StringUtils.equals(sysVal, "JRTJ")) {
                cvChkPlanDetail.setDateVal(cvChkIndRuleChk.getBigFocusFrq());
            }
            cvChkPlanDetail.setSysVal(sysVal);
            cvChkPlanDetail.setExecSql(cvChkIndRuleChk.getChkSql());
            cvChkPlanDetail.setDataTime(dataTime);
            cvChkPlanDetailList.add(cvChkPlanDetail);
        }
        return detailService.saveBatch(cvChkPlanDetailList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePlan(String id) {
        boolean p = this.removeById(id);
        QueryWrapper<CvChkPlanDetail> detailQueryWrapper = new QueryWrapper<>();
        detailQueryWrapper.lambda().eq(CvChkPlanDetail::getPlanId, id);
        boolean pd = detailService.remove(detailQueryWrapper);
        QueryWrapper<CvChkPlanResult> resultQueryWrapper = new QueryWrapper<>();
        resultQueryWrapper.lambda().eq(CvChkPlanResult::getPlanId, id);
        boolean pr = resultService.remove(resultQueryWrapper);
        return p && pd && pr;
    }

    private String getSysList() {
        List<SysDictItemVo> sysDictItems = dictItemService.getByDictCode("IND_SYS");
        List<String> sysValList = sysDictItems.stream().map(SysDictItemVo::getItemValue).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sysValList.size(); i++) {
            builder.append(sysValList.get(i));
            if (i == sysValList.size() - 1) {
                break;
            }
            builder.append(",");
        }
        return builder.toString();
    }
}
