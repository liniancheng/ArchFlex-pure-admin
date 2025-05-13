package com.adtec.rdc.base.tenant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.app.constants.AppConstants;
import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.tenant.enums.AppFlagEnums;
import com.adtec.rdc.base.tenant.mapper.SysAppInfoMapper;
import com.adtec.rdc.base.tenant.mapper.SysMenuTmpMapper;
import com.adtec.rdc.base.tenant.model.bo.SysAppTree;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;
import com.adtec.rdc.base.tenant.model.po.SysMenuTmp;
import com.adtec.rdc.base.tenant.service.SysAppInfoService;
import com.adtec.rdc.base.tenant.util.AppTreeUtil;
import com.adtec.rdc.base.tenant.util.MenuTmpUtil;
import com.adtec.rdc.base.user.mapper.SysMenuInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleMenuRelMapper;
import com.adtec.rdc.base.user.mapper.SysRoleUserRelMapper;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;
import com.adtec.rdc.base.user.model.po.SysRoleUserRel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@Service
public class SysAppInfoServiceImpl extends BaseServiceImpl<SysAppInfoMapper, SysAppInfo> implements SysAppInfoService {
	@Autowired
	private SysAppInfoMapper mapper;
	
	@Autowired
	private SysMenuTmpMapper sysMenuTmpMapper;
	
	@Autowired
	private SysMenuInfoMapper sysMenuInfoMapper;
	
	@Autowired
	private SysRoleInfoMapper sysRoleInfoMapper;
	
	@Autowired
	private SysRoleMenuRelMapper sysRoleMenuRelMapper;
	
	@Autowired
	private SysRoleUserRelMapper sysRoleUserRelMapper;

	@Override
	public boolean saveApp(SysAppInfo app, String userId) {
		if(mapper.selectById(app.getAppId()) !=null) {
			throw new ServiceException("已存在相同租户编号！");
		}
		if(mapper.getCountByName(app.getAppName())>0) {
			throw new ServiceException("已存在相同租户名称！");
		}
		mapper.insert(app);
		// 创建租户菜单 从菜单模板表复制一份
		String appFlag = AppFlagEnums.RUN.getCode();
		if(StringUtils.isEmpty(app.getAppRel())) {
			appFlag = AppFlagEnums.EXP.getCode();
		}
		List<SysMenuTmp> tmpMenuList = sysMenuTmpMapper.queryMenuTmps(appFlag);
		List<SysMenuInfo> menuList = MenuTmpUtil.menuTmpToMenu(tmpMenuList, app.getAppId());
		//批量入库菜单
		sysMenuInfoMapper.batchInsertMenus(menuList);
		// 新增默认角色及菜单权限
		SysRoleInfo roleInfo = MenuTmpUtil.getDefaultRoleInfo(app.getAppId(), app.getAppName());
		sysRoleInfoMapper.insert(roleInfo);
		// 角色权限
		List<SysRoleMenuRel> menuRels = MenuTmpUtil.getDefaultRoleMenu(menuList, roleInfo.getRoleId());
		sysRoleMenuRelMapper.batchInsertMenuRel(menuRels);
		//用户角色
		SysRoleUserRel userRole = MenuTmpUtil.getDefaultRoleUser(roleInfo.getRoleId(), userId, app.getAppId());
		sysRoleUserRelMapper.insert(userRole);
		
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteById(String id) {
		if(AppConstants.DEF_MAIN_APP_ID.equals(id)) {
			throw new ServiceException("当前系统不可删除！");
		}
		List<SysAppInfo> upApp = new ArrayList<SysAppInfo>(2);
		SysAppInfo app = new SysAppInfo();
		app.setAppId(id);
		app.setAppState(AppConstants.APP_STATE_DEL);
		upApp.add(app);
		List<SysAppInfo> deApp = mapper.getAppsByAppId(id);
		deApp.forEach(a ->{
			if(!AppConstants.APP_STATE_DEL.equals(a.getAppState())) {
				a.setAppState(AppConstants.APP_STATE_DEL);
				upApp.add(a);
			}
		});
		mapper.updateStateById(upApp);
		return false;
	}

	@Override
	public List<SysAppTree> getAllAppTree(SysAppInfo app) {
		QueryWrapper<SysAppInfo> query  = new QueryWrapper<SysAppInfo>();
		if(StringUtils.isNotBlank(app.getAppName())) {
			query.lambda().like(SysAppInfo::getAppName, app.getAppName());
		}
		List<SysAppInfo> list = mapper.selectList(query);
		return AppTreeUtil.list2Tree(list);
	}

	@Override
	public int getAppRelCount(String id) {
		QueryWrapper<SysAppInfo> wraper = new QueryWrapper<>();
		wraper.lambda().eq(SysAppInfo::getAppRel, id).notLike(SysAppInfo::getAppState, AppConstants.APP_STATE_DEL);
		return mapper.selectCount(wraper);
	}

	@Override
	public Boolean updateByAppId(SysAppInfo app) {
		QueryWrapper<SysAppInfo> wraper = new QueryWrapper<>();
		wraper.lambda().eq(SysAppInfo::getAppName, app.getAppName());
		SysAppInfo info = mapper.selectOne(wraper);
		if(info!=null && !app.getAppId().equals(info.getAppId())) {
			throw new ServiceException("已存在相同租户名称！");
		}
		mapper.updateById(app);
		return Boolean.TRUE;
	}

	@Override
	public List<SysAppTree> getAppAuthByUserId(String userId) {
		List<SysAppInfo> list = mapper.getAppsByUserId(userId, null, AppConstants.APP_STATE_ON);
		return AppTreeUtil.list2Tree2(list);
	}

}
