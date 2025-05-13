package com.adtec.rdc.base.layout.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.layout.enums.LayoutEnums;
import com.adtec.rdc.base.layout.mapper.SysLayoutInfoMapper;
import com.adtec.rdc.base.layout.mapper.SysLayoutItemInfoMapper;
import com.adtec.rdc.base.layout.mapper.SysLayoutItemRelMapper;
import com.adtec.rdc.base.layout.mapper.SysRoleLayoutRelMapper;
import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemInfo;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;
import com.adtec.rdc.base.layout.model.po.SysRoleLayoutRel;
import com.adtec.rdc.base.layout.model.query.SysLayoutInfoQuery;
import com.adtec.rdc.base.layout.service.SysLayoutInfoService;
import com.adtec.rdc.base.layout.utils.LayoutUtils;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dengchf
 * @date 2020-08-25 15:04:09
 */
@Service
public class SysLayoutInfoServiceImpl extends BaseServiceImpl<SysLayoutInfoMapper, SysLayoutInfo>
		implements SysLayoutInfoService {
	@Autowired
	private SysLayoutInfoMapper mapper;
	@Autowired
	private SysLayoutItemInfoMapper itemMapper;
	@Autowired
	private SysRoleLayoutRelMapper roleRelMapper;
	@Autowired
	private SysLayoutItemRelMapper itemRelMapper;

	@Override
	public SysLayoutInfoQuery pageByQuery(SysLayoutInfoQuery query) {
		query.setDesc("CREATE_TIME", "MODIFY_TIME");
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public boolean save(SysLayoutInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_RE_NAME.getErrorCode(),
					ErrorCodeEnum.LAYOUT_RE_NAME.getMessage());
		}
		if (isExistLevel(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_RE_LEVEL.getErrorCode(),
					ErrorCodeEnum.LAYOUT_RE_LEVEL.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	public boolean updateById(SysLayoutInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_RE_NAME.getErrorCode(),
					ErrorCodeEnum.LAYOUT_RE_NAME.getMessage());
		}
		if (isExistLevel(entity)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_RE_LEVEL.getErrorCode(),
					ErrorCodeEnum.LAYOUT_RE_LEVEL.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}

	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		QueryWrapper<SysRoleLayoutRel> query = new QueryWrapper<SysRoleLayoutRel>();
		query.lambda().eq(SysRoleLayoutRel::getLayId, id);
		roleRelMapper.delete(query);
		QueryWrapper<SysLayoutItemRel> itemQuery = new QueryWrapper<SysLayoutItemRel>();
		itemQuery.lambda().eq(SysLayoutItemRel::getLayId, id);
		itemRelMapper.delete(itemQuery);
		return super.removeById(id);
	}

	@Override
	public boolean isExistName(SysLayoutInfo entity) {
		return mapper.isExistName(entity);
	}

	@Override
	public boolean isExistLevel(SysLayoutInfo entity) {
		return mapper.isExistLevel(entity);
	}

	@Override
	public SysLayoutInfo fetchByLoginName(String loginName, String appId) {
		if (StringUtils.isEmpty(loginName)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_NULL_USER.getErrorCode(),
					ErrorCodeEnum.LAYOUT_NULL_USER.getMessage());
		}
		if (StringUtils.isEmpty(appId)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_NULL_APP.getErrorCode(),
					ErrorCodeEnum.LAYOUT_NULL_APP.getMessage());
		}
		QueryWrapper<SysLayoutInfo> query = new QueryWrapper<SysLayoutInfo>();
		query.lambda().eq(SysLayoutInfo::getAppId, appId).eq(SysLayoutInfo::getLoginName, loginName)
				.eq(SysLayoutInfo::getLayType, LayoutEnums.TYPE_CUSTOM.getCode());
		SysLayoutInfo info = mapper.selectOne(query);
		if (info == null) {
			StringBuffer sb = new StringBuffer();
			sb.append(loginName).append("-").append(appId).append("-自定义布局");
			info = new SysLayoutInfo();
			info.setAppId(appId).setLayName(sb.toString()).setLoginName(loginName).setCreateTime(LocalDateTime.now())
					.setLayLevel(0).setLayType(LayoutEnums.TYPE_CUSTOM.getCode());
			super.save(info);
		}
		return info;
	}

	@Override
	public SysLayoutInfo fetchOnce(String id, String appId) {
		SysLayoutInfo info = super.getById(id);
		if (info != null) {
			List<SysLayoutItemInfo> list = itemMapper.listItems(id, appId);
			info.setListItems(list);
		}
		return info;
	}

	@Override
	public SysLayoutInfo fetchLayout(String loginName, String appId, List<String> roleIds) {
		SysLayoutInfoQuery query = new SysLayoutInfoQuery();
		query.setAppId(appId);
		query.setLoginName(loginName);
		query.setRoleIds(roleIds);
		query = mapper.queryOne(query);
		SysLayoutInfo info = null;
		if (query.getTotal() > 0) {
			info = query.getRecords().get(0);
			List<SysLayoutItemInfo> list = itemMapper.listItems(info.getLayId(), appId);
			info.setListItems(list);
		} else {
			info = LayoutUtils.getInstance().getDefault();
		}
		return info;
	}

}
