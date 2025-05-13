package com.adtec.rdc.base.crossvalidation.service.impl;

import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysResInfoVo;
import com.adtec.rdc.base.crossvalidation.mapper.CvChkPlanDetailMapper;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkPlanDetailBo;
import com.adtec.rdc.base.crossvalidation.model.bo.JdbcSource;
import com.adtec.rdc.base.crossvalidation.model.bo.ResultBo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlan;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanDetailService;

import com.adtec.rdc.base.crossvalidation.service.CvChkPlanResultService;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanService;
import com.adtec.rdc.base.crossvalidation.util.CheckSqlUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
@Service
public class CvChkPlanDetailServiceImpl extends BaseServiceImpl<CvChkPlanDetailMapper, CvChkPlanDetail> implements CvChkPlanDetailService {
	@Autowired
    private CvChkPlanDetailMapper mapper;
	@Autowired
	private CvChkPlanService planService;
	@Autowired
	private CvChkPlanResultService resultService;
	@Autowired
	private BaseUserService baseUserService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean execSql(CvChkPlanDetailBo cvChkPlanDetailBo) {
		String planId = cvChkPlanDetailBo.getPlanId();
		String dataTime = cvChkPlanDetailBo.getDataTime();
		String resVal = cvChkPlanDetailBo.getResVal();
		SysResInfoVo sysResInfo = baseUserService.queryById(resVal);
		if (sysResInfo == null){
			throw new ServiceException("获取数据资源配置失败,请重新选择!");
		}
		JdbcSource jdbcSource = new JdbcSource();
		jdbcSource.setDriverClass(sysResInfo.getLkDriver());
		jdbcSource.setUrl(sysResInfo.getLkUrl());
		jdbcSource.setUsername(sysResInfo.getLkUser());
		jdbcSource.setPassword(sysResInfo.getLkPassword());
		//查询计划
		CvChkPlan cvChkPlan = planService.getById(planId);
		if (cvChkPlan == null){
			throw new ServiceException("当前计划对应的数据不存在,请创建计划后操作!");
		}
		// TODO 获取中间价列表
		List<ResultBo> zjjList = CheckSqlUtil.selectWbAndZjj(jdbcSource,dataTime,cvChkPlan.getOrgVal());
		//获取明细
		QueryWrapper<CvChkPlanDetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(CvChkPlanDetail::getPlanId, planId);
		List<CvChkPlanDetail> cvChkPlanDetailList = mapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(cvChkPlanDetailList)){
			throw new ServiceException("当前计划对应的详细数据不存在,请创建计划后操作!");
		}
		for (CvChkPlanDetail cvChkPlanDetail : cvChkPlanDetailList) {
			String execSql = cvChkPlanDetail.getExecSql();
			if (StringUtils.isNotBlank(execSql)) {
				// 替换sql中的数据
				StringBuilder sb = new StringBuilder();
				//追加‘
				sb.append("'");
				//法人赋值
				sb.append(cvChkPlan.getOrgVal());
				//追加‘
				sb.append("'");
				//法人
				execSql = execSql.replace("$lpInstId", sb.toString());
				//数据时间
				execSql = execSql.replace("$date", "'" + dataTime + "'");
			}
			cvChkPlanDetail.setExecSql(execSql);
		}
		List<CvChkPlanDetail> planDetailResults = CheckSqlUtil.execSql(cvChkPlanDetailList,zjjList,jdbcSource);
		//保存计划结果
		List<CvChkPlanResult> cvChkPlanResultList = new ArrayList<>();
		for (CvChkPlanDetail planDetailResult : planDetailResults) {
			CvChkPlanResult cvChkPlanResult = new CvChkPlanResult();
			cvChkPlanResult.setPlanId(planId);
			cvChkPlanResult.setIndNo(planDetailResult.getIndNo());
			cvChkPlanResult.setOrgVal(planDetailResult.getOrgVal());
			cvChkPlanResult.setDateVal(planDetailResult.getDateVal());
			cvChkPlanResult.setCurrencyVal(planDetailResult.getResultBo().getBZ());
			cvChkPlanResult.setSysVal(planDetailResult.getSysVal());
			cvChkPlanResult.setResultVal(planDetailResult.getResultBo().getYE());
			cvChkPlanResultList.add(cvChkPlanResult);
		}
		boolean result = resultService.saveBatch(cvChkPlanResultList);
		//将计划明细改为已检核
		cvChkPlanDetailList = new ArrayList<>();
		for (CvChkPlanDetail planDetailResult : planDetailResults) {
			planDetailResult.setResultStatus("01");
			cvChkPlanDetailList.add(planDetailResult);
		}
		boolean detail = this.updateBatchById(cvChkPlanDetailList);
		//将计划状态改为已完成
		cvChkPlan.setPlanState("2");
		boolean plan = planService.updateById(cvChkPlan);
		return result && detail && plan;
	}
}
