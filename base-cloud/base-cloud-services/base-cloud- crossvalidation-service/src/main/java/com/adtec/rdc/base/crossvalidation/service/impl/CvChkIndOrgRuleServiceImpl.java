package com.adtec.rdc.base.crossvalidation.service.impl;

import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkIndMapper;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkIndOrgRuleMapper;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkInd;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndOrgRule;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndOrgRuleQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndOrgRuleService;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndRuleChkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
@Service
public class CvChkIndOrgRuleServiceImpl extends BaseServiceImpl<CvChkIndOrgRuleMapper, CvChkIndOrgRule> implements CvChkIndOrgRuleService {
	@Autowired
    private CvChkIndOrgRuleMapper mapper;
	@Autowired
	private CvChkIndRuleChkService ruleChkService;
	@Autowired
	private CvChkIndMapper cvChkIndMapper;
	@Autowired
	private BaseUserService branchInfoService;

	@Override
	public CvChkIndOrgRuleQuery pageByQuery(CvChkIndOrgRuleQuery query) {
		query.setAsc("IND_NO");
		mapper.pageByQuery(query);
		List<CvChkIndOrgRule> chkIndOrgRules = query.getRecords();
		if (CollectionUtils.isNotEmpty(chkIndOrgRules)) {
			// 设置指标名称
			List<String> indNos = chkIndOrgRules.stream().map(CvChkIndOrgRule::getIndNo).collect(Collectors.toList());
			List<CvChkInd> cvChkIndList = cvChkIndMapper.selectBatchIds(indNos);
			for (CvChkIndOrgRule chkIndOrgRule : chkIndOrgRules) {
				for (CvChkInd cvChkInd : cvChkIndList) {
					if (StringUtils.equals(chkIndOrgRule.getIndNo(),cvChkInd.getIndNo())){
						chkIndOrgRule.setIndNm(cvChkInd.getIndNm());
					}
				}
			}
			// 设置法人名称
			List<String> orgVals = chkIndOrgRules.stream().map(CvChkIndOrgRule::getOrgVal).collect(Collectors.toList());
			List<SysBranchInfoVo> sysBranchInfoList = branchInfoService.selectBatchByIds(orgVals);
			for (CvChkIndOrgRule chkIndOrgRule : chkIndOrgRules) {
				for (SysBranchInfoVo sysBranchInfo : sysBranchInfoList) {
					if (StringUtils.equals(chkIndOrgRule.getOrgVal(),sysBranchInfo.getBranchNo())){
						chkIndOrgRule.setOrgNm(sysBranchInfo.getBranchName());
					}
				}
			}
		}
		return query;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveCvChkIndOrgRule(CvChkIndOrgRule cvindorg) {
		List<CvChkIndOrgRule> list = new ArrayList<>();
		List<CvChkIndRuleChk> saveCvChkIndRuleChkList = new ArrayList<>();
		List<String> indNos = cvindorg.getIndNos();
		List<String> orgVals = cvindorg.getOrgVals();
		if (CollectionUtils.isNotEmpty(orgVals) && CollectionUtils.isNotEmpty(indNos)) {
			// 指标法人配置
			setIndOrg(cvindorg, list, indNos, orgVals);
			// 指标规则设置
			setIndRule(saveCvChkIndRuleChkList, indNos, orgVals);
		}else {
			throw new ServiceException("请选择指标及法人!");
		}
		//保存指标法人配置
		boolean saveBatch = this.saveBatch(list);
		//保存指标规则
		boolean batch = ruleChkService.saveBatch(saveCvChkIndRuleChkList);
		return saveBatch && batch;
	}

	/**
	 * 指标规则设置
	 * @param saveCvChkIndRuleChkList
	 * @param indNos
	 * @param orgVals
	 */
	private void setIndRule(List<CvChkIndRuleChk> saveCvChkIndRuleChkList, List<String> indNos, List<String> orgVals) {
		QueryWrapper<CvChkIndRuleChk> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().in(CvChkIndRuleChk::getIndNo,indNos).eq(CvChkIndRuleChk::getRuleVal,"01");
		List<CvChkIndRuleChk> cvChkIndRuleChkList = ruleChkService.list(queryWrapper);
		for (CvChkIndRuleChk cvChkIndRuleChk : cvChkIndRuleChkList) {
			if (StringUtils.isNotBlank(cvChkIndRuleChk.getOrgVal())){
				continue;
			}
			for (String orgVal : orgVals) {
				queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(CvChkIndRuleChk::getIndNo,cvChkIndRuleChk.getIndNo()).eq(CvChkIndRuleChk::getOrgVal,orgVal);
				ruleChkService.remove(queryWrapper);
				CvChkIndRuleChk ruleChk = new CvChkIndRuleChk();
				cvChkIndRuleChk.setOrgVal(orgVal);
				cvChkIndRuleChk.setId(null);
				BeanUtils.copyProperties(cvChkIndRuleChk,ruleChk);
				saveCvChkIndRuleChkList.add(ruleChk);
			}
		}
	}

	/**
	 * 指标法人配置
	 * @param cvindorg
	 * @param list
	 * @param indNos
	 * @param orgVals
	 */
	private void setIndOrg(CvChkIndOrgRule cvindorg, List<CvChkIndOrgRule> list, List<String> indNos, List<String> orgVals) {
		for (String indNo : indNos) {
			for (String orgVal : orgVals) {
				QueryWrapper<CvChkIndOrgRule> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(CvChkIndOrgRule::getIndNo,indNo).eq(CvChkIndOrgRule::getOrgVal,orgVal);
				CvChkIndOrgRule orgRule = mapper.selectOne(queryWrapper);
				if (orgRule != null){
					continue;
				}
				CvChkIndOrgRule cvChkIndOrgRule = new CvChkIndOrgRule();
				cvChkIndOrgRule.setOrgVal(orgVal);
				cvChkIndOrgRule.setIndNo(indNo);
				cvChkIndOrgRule.setIsUse(cvindorg.getIsUse());
				cvChkIndOrgRule.setIsShow(cvindorg.getIsShow());
				list.add(cvChkIndOrgRule);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(String id) {
		CvChkIndOrgRule cvChkIndOrgRule = mapper.selectById(id);
		boolean remove = this.removeById(id);
		QueryWrapper<CvChkIndRuleChk> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(CvChkIndRuleChk::getIndNo,cvChkIndOrgRule.getIndNo())
				.eq(CvChkIndRuleChk::getOrgVal,cvChkIndOrgRule.getOrgVal());
		boolean remove1 = ruleChkService.remove(queryWrapper);
		return remove && remove1;
	}
}
