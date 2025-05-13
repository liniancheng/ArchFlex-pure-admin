package com.adtec.rdc.base.layout.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.layout.mapper.SysLayoutItemInfoMapper;
import com.adtec.rdc.base.layout.mapper.SysLayoutItemRelMapper;
import com.adtec.rdc.base.layout.mapper.SysRoleLayoutItemRelMapper;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemInfo;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;
import com.adtec.rdc.base.layout.model.po.SysRoleLayoutItemRel;
import com.adtec.rdc.base.layout.model.query.SysLayoutItemInfoQuery;
import com.adtec.rdc.base.layout.service.SysLayoutItemInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengchf
 * @date 2020-08-25 15:17:06
 */
@Service
public class SysLayoutItemInfoServiceImpl extends BaseServiceImpl<SysLayoutItemInfoMapper, SysLayoutItemInfo>
		implements SysLayoutItemInfoService {
	@Autowired
	private SysLayoutItemInfoMapper mapper;
	@Autowired
	private SysRoleLayoutItemRelMapper roleRelMapper;
	@Autowired
	private SysLayoutItemRelMapper itemRelMapper;

	@Override
	public SysLayoutItemInfoQuery pageByQuery(SysLayoutItemInfoQuery query) {
		query.setAsc("ITEM_ORDER");
		query.setDesc("CREATE_TIME", "MODIFY_TIME");
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public SysLayoutItemInfoQuery fetchPerson(SysLayoutItemInfoQuery query, List<String> list) {
		query.setAsc("ITEM_ORDER");
		query.setDesc("CREATE_TIME", "MODIFY_TIME");
		query.setRoleIds(list);
		query = mapper.fetchPerson(query);
		return query;
	}

	@Override
	public boolean save(SysLayoutItemInfo entity) {
		if (isExist(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ITEM_RE_NAME.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ITEM_RE_NAME.getMessage());
		}

		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	public boolean updateById(SysLayoutItemInfo entity) {
		if (isExist(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ITEM_RE_NAME.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ITEM_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}

	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		// 删除角色权限
		QueryWrapper<SysRoleLayoutItemRel> query = new QueryWrapper<SysRoleLayoutItemRel>();
		query.lambda().eq(SysRoleLayoutItemRel::getItemId, id);
		roleRelMapper.delete(query);
		// 删除布局关系
		QueryWrapper<SysLayoutItemRel> relQuery = new QueryWrapper<SysLayoutItemRel>();
		relQuery.lambda().eq(SysLayoutItemRel::getItemId, id);
		itemRelMapper.delete(relQuery);
		return super.removeById(id);
	}

	@Override
	public boolean isExist(SysLayoutItemInfo entity) {
		return mapper.isExist(entity);
	}

}
