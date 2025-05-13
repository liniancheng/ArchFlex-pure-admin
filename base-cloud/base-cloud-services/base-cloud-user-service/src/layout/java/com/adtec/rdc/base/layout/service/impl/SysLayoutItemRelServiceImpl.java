package com.adtec.rdc.base.layout.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.layout.mapper.SysLayoutItemRelMapper;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;
import com.adtec.rdc.base.layout.service.SysLayoutItemRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengchf
 * @date 2020-08-25 15:08:26
 */
@Service
public class SysLayoutItemRelServiceImpl extends BaseServiceImpl<SysLayoutItemRelMapper, SysLayoutItemRel>
		implements SysLayoutItemRelService {
	@Autowired
	private SysLayoutItemRelMapper mapper;

	@Override
	public boolean isExist(SysLayoutItemRel entity) {
		return mapper.isExist(entity);
	}

	@Override
	@Transactional
	public boolean saveLists(List<SysLayoutItemRel> listRels, String layId) {
		QueryWrapper<SysLayoutItemRel> query = new QueryWrapper<SysLayoutItemRel>();			
		query.lambda().eq(SysLayoutItemRel::getLayId, layId);
		mapper.delete(query);
		if (listRels != null && listRels.size() > 0) {
			return super.saveBatch(listRels);
		}
		return Boolean.TRUE;
	}

	@Override
	public List<SysLayoutItemRel> listRels(String id, String appId) {
		return mapper.listRels(id, appId);
	}

}
