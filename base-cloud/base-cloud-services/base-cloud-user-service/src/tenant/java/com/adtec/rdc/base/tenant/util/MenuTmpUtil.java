package com.adtec.rdc.base.tenant.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.tenant.model.po.SysMenuTmp;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;
import com.adtec.rdc.base.user.model.po.SysRoleUserRel;

public class MenuTmpUtil {
	
	public static List<SysMenuInfo> menuTmpToMenu (List<SysMenuTmp> tmpMenus, String appId) {
		List<SysMenuInfo> menuList = new ArrayList<SysMenuInfo>();
		Map<String,SysMenuTmp> menuMap = new HashMap<String, SysMenuTmp>();
		tmpMenus.forEach(t -> menuMap.put(t.getMenuId(), t));
		menuMap.forEach((k,v)->{
			SysMenuInfo sysMenu = menuTmpToMenuModel(v, appId);
			sysMenu.setMenuId(k + appId);
			if(!CommonConstants.TREE_ROOT.equals(sysMenu.getParentId())){
				sysMenu.setParentId(menuMap.get(sysMenu.getParentId()).getMenuId()+appId);
			}
			menuList.add(sysMenu);
		});
		return menuList;
	}
	
	private static SysMenuInfo menuTmpToMenuModel (SysMenuTmp tmp, String appId) {
		SysMenuInfo menu = new SysMenuInfo();
		menu.setAppId(appId);
		menu.setButtonPermission(tmp.getButtonPermission());
		menu.setComponentPath(tmp.getComponentPath());
		menu.setCreateTime(LocalDateTime.now());
		menu.setDelFlag(tmp.getDelFlag());
		menu.setHttpMethod(tmp.getHttpMethod());
		menu.setMenuColor(tmp.getMenuColor());
		menu.setMenuId(tmp.getMenuId());
		menu.setMenuName(tmp.getMenuName());
		menu.setMenuPath(tmp.getMenuPath());
		menu.setMenuRouteName(tmp.getMenuRouteName());
		menu.setMenuSort(tmp.getMenuSort());
		menu.setMenuType(tmp.getMenuType());
		menu.setMenuUrl(tmp.getMenuUrl());
		menu.setModifyTime(LocalDateTime.now());
		menu.setParentId(tmp.getParentId());
		return menu;
	}
	
	public static SysRoleInfo getDefaultRoleInfo(String appId, String appName) {
		
		SysRoleInfo role = new SysRoleInfo();
		role.setAppId(appId);
		role.setCreateTime(LocalDateTime.now());
		role.setDelFlag("0");
		role.setModifyTime(LocalDateTime.now());
		role.setRoleCode(appId+"_default");
		role.setRoleId(UUID.generate());
		role.setRoleName(appName+"_默认角色");
		return role;
	}
	
	public static List<SysRoleMenuRel> getDefaultRoleMenu(List<SysMenuInfo> menus, String roleId){
		List<SysRoleMenuRel> menuAuth = new ArrayList<SysRoleMenuRel>();
		for (SysMenuInfo menu : menus) {
			SysRoleMenuRel rel = new SysRoleMenuRel();
			rel.setRoleId(roleId);
			rel.setMenuId(menu.getMenuId());
			menuAuth.add(rel);
		}
		return menuAuth;
	}
	
	public static SysRoleUserRel getDefaultRoleUser(String roleId, String userId, String appId) {
		SysRoleUserRel rel = new SysRoleUserRel();
		rel.setRoleId(roleId);
		rel.setUserId(userId);
		return rel;
	}
	
}
