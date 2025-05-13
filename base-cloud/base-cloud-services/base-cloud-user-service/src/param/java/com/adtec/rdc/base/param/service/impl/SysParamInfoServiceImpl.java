package com.adtec.rdc.base.param.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.param.mapper.SysParamInfoMapper;
import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.adtec.rdc.base.param.query.SysParamInfoQuery;
import com.adtec.rdc.base.param.service.SysParamInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class SysParamInfoServiceImpl extends BaseServiceImpl<SysParamInfoMapper, SysParamInfo> implements SysParamInfoService{

	@Autowired
	SysParamInfoMapper mapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	
	@Override
	public SysParamInfoQuery pageByQuery(SysParamInfoQuery query) {
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public boolean isExistParamName(SysParamInfo param) {
		return mapper.isExistParamName(param);
	}

	@Override
	public boolean onlySave(SysParamInfo param) {
		if(isExistParamName(param)) {
			throw new ServiceException("参数名称已存在！");
		}
		param.setCreateTime(LocalDateTime.now());
		boolean ok = super.save(param);
		refresh();
		return ok;
	}

	@Override
	public boolean onlyUpdate(SysParamInfo param) {
		if(isExistParamName(param)) {
			throw new ServiceException("参数名称已存在！");
		}
		param.setModifyTime(LocalDateTime.now());
		boolean ok = super.updateById(param);
		refresh();
		return ok;
	}

	@Override
	public String getParam(String paramName, String appId) {
		QueryWrapper<SysParamInfo> wrapper = new QueryWrapper<SysParamInfo>();
		wrapper.lambda().eq(SysParamInfo::getParamName, paramName).eq(SysParamInfo::getAppId, appId);
		List<SysParamInfo> list = Optional.ofNullable(mapper.selectList(wrapper)).orElse(new ArrayList<SysParamInfo>());
		for (SysParamInfo param : list) {
			return param.getParamValue();
		}
		return null;
	}
	
	public void refresh() {
		List<SysParamInfo> list = super.list();
		if (list != null && list.size() > 0) {
			Map<String, String> map = list.stream()
					.collect(Collectors.toMap(SysParamInfo::getParamName, b -> b.getParamValue(), (k1, k2) -> k2));
			stringRedisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).putAll(map);
		} else {
			stringRedisTemplate.delete(CommonConstants.SYSTEM_PUBLIC_CODE);
		}
	}
	
}
