package com.adtec.rdc.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.constants.PubCodeConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.vo.SysRoleVo;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.user.mapper.SysMenuInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleMenuRelMapper;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;
import com.adtec.rdc.base.user.model.query.SysRoleInfoQuery;
import com.adtec.rdc.base.user.service.SysRoleInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author: JTao
 * @date: 2018/11/1 15:42
 */
@Service
public class SysRoleInfoServiceImpl extends ServiceImpl<SysRoleInfoMapper, SysRoleInfo> implements SysRoleInfoService {

	@Autowired
	private SysRoleInfoMapper sysRoleInfoMapper;
	@Autowired
	private SysRoleMenuRelMapper sysRoleMenuRelMapper;
	@Autowired
	private SysMenuInfoMapper sysMenuInfoMapper;
	@Autowired
	private MessageQueueService messageQueueService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean save(SysRoleInfo role) {
		if(sysRoleInfoMapper.findRoleByCode(role.getRoleCode(), role.getAppId()) >0) {
			throw new ServiceException(ErrorCodeEnum.ROLE_RE_CODE.getErrorCode(),
					ErrorCodeEnum.ROLE_RE_CODE.getMessage());
		}
		if(sysRoleInfoMapper.findRoleByName(role.getRoleName(), role.getAppId())>0) {
			throw new ServiceException(ErrorCodeEnum.ROLE_RE_NAME.getErrorCode(),
					ErrorCodeEnum.ROLE_RE_NAME.getMessage());
		}
		role.setCreateTime(LocalDateTime.now());
		sysRoleInfoMapper.insert(role);
		bindRoleWithMenu(role);
		
		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			//发送消息-新增角色
			role.setDelFlag("0");
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_ROLE_QUEUE, role);
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateById(SysRoleInfo role) {
		if(role.getMenuIds() != null && role.getMenuIds().size() >0 ){
			deleteBindRoleWithMenu(role.getRoleId());
			bindRoleWithMenu(role);
		}
		role.setModifyTime(LocalDateTime.now());
		sysRoleInfoMapper.updateById(role);
		
		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			//发送消息-维护角色
			role.setDelFlag("0");
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_ROLE_QUEUE, role);
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean deleteById(String roleId) {
		SysRoleInfo role = sysRoleInfoMapper.selectById(roleId);
		sysRoleInfoMapper.deleteById(roleId);
		deleteBindRoleWithMenu(roleId);
		
		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			//发送消息-删除角色
			role.setDelFlag("1");
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_ROLE_QUEUE, role);
		}
		return Boolean.TRUE;
	}

	@Override
	public SysRoleInfo getRoleInfoWithMenuByRoleId(String roleId) {
		SysRoleInfo role = sysRoleInfoMapper.selectById(roleId);
		QueryWrapper<SysRoleMenuRel> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SysRoleMenuRel::getRoleId, roleId);
		List<SysRoleMenuRel> roleMenus = sysRoleMenuRelMapper.selectList(queryWrapper);
		List<String> menuIds = roleMenus.stream()
				.map(SysRoleMenuRel::getMenuId)
				.collect(Collectors.toList());
		deleteParentMenuIds(menuIds, role.getAppId());
		role.setMenuIds(menuIds);
		return role;
	}

	@Override
	public SysRoleInfoQuery pageByQuery(SysRoleInfoQuery query) {
		query.setDesc("create_time", "modify_time");
		sysRoleInfoMapper.pageByQuery(query);
		return query;
	}

	@Override
	public List<SysRoleInfo> listSysRole(String appId) {
		QueryWrapper<SysRoleInfo> query = new QueryWrapper<SysRoleInfo>();
		query.lambda().eq(SysRoleInfo::getAppId, appId);
		return sysRoleInfoMapper.selectList(query);
	}

	/**
	 * 绑定角色与资源信息
	 * @param sysRoleDTO
	 */
	private void bindRoleWithMenu(SysRoleInfo role) {
		if(role.getMenuIds() == null || role.getMenuIds().size() == 0) {
			return;
		}
		List<SysMenuInfo> allMenus = sysMenuInfoMapper.findAllMenusByAppId(role.getAppId());
		Map<String, String> menuIdAndParentIdMap = new HashMap<String,String>();
		allMenus.forEach(menu -> {
			menuIdAndParentIdMap.put(menu.getMenuId(), menu.getParentId());
		});
		List<String> menuIds = role.getMenuIds();
		Set<String> insertMenuIds = new HashSet<String>();
		for (String menuId : menuIds) {
			insertMenuIds.add(menuId);
			//递归上级
			String parentId = menuIdAndParentIdMap.get(menuId);
			while(parentId!=null && !CommonConstants.TREE_ROOT.equals(parentId)) {
				insertMenuIds.add(parentId);
				parentId = menuIdAndParentIdMap.get(parentId);
			}
		}
		List<SysRoleMenuRel> menuRels = new ArrayList<SysRoleMenuRel>(insertMenuIds.size());
		for(String menuId : insertMenuIds) {
			SysRoleMenuRel rel = new SysRoleMenuRel();
			rel.setMenuId(menuId);
			rel.setRoleId(role.getRoleId());
			menuRels.add(rel);
		}
		sysRoleMenuRelMapper.batchInsertMenuRel(menuRels);
	}

	/**
	 * 删除角色与绑定资源信息
	 * @param roleId
	 */
	private Integer deleteBindRoleWithMenu(String roleId) {
		QueryWrapper<SysRoleMenuRel> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(SysRoleMenuRel::getRoleId, roleId);
		return sysRoleMenuRelMapper.delete(wrapper);
	}
	
	/**
	 * 父节点的子节点没有全部选中，删除父节点，不做展示
	 */
	private void deleteParentMenuIds(List<String> menuIds, String appId) {
		List<SysMenuInfo> allMenus = sysMenuInfoMapper.findAllMenusByAppId(appId);
		Map<String,List<String>> allMenuMap = new HashMap<String, List<String>>();
		allMenus.forEach(menu -> {
			if (!allMenuMap.containsKey(menu.getParentId())) {
				allMenuMap.put(menu.getParentId(), new ArrayList<String>());
			}
			allMenuMap.get(menu.getParentId()).add(menu.getMenuId());
		});
		List<String> rmList = new ArrayList<String>();
		menuIds.forEach(menuId -> {
			List<String> nodeIds = allMenuMap.get(menuId);
			while (nodeIds!=null) {
				for (String nodeId : nodeIds) {
					if(!menuIds.contains(nodeId)) {
						rmList.add(menuId);
					}
					nodeIds = allMenuMap.get(nodeId);
				}
			}
		});
		menuIds.removeAll(rmList);
	}

	@Override
	public List<SysRoleVo> queryRolesByRoleCodes(String appId, String roleCodes) {
		return sysRoleInfoMapper.queryRolesByAppIdAndRoleCodes(appId,Arrays.asList(roleCodes.split(",")));
	}

	@Override
	public List<SysRoleVo> queryExistRolesByRoleCodes(String appId, String roleCodes) {
		return sysRoleInfoMapper.queryExistRolesByRoleCodes(appId,Arrays.asList(roleCodes.split(",")));
	}

	@Override
	public List<SysRoleVo> queryRoleVos(String appId) {
		return sysRoleInfoMapper.queryRoleVos(appId);
	}
}
