package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.user.model.bo.SysMenuTree;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author: JTao
 * @date: 2018/10/17 13:58
 * @description: 系统菜单服务类
 */
public interface SysMenuInfoService extends IService<SysMenuInfo> {

    /**
     * 删除菜单以及子菜单
     * @param id
     * @param appId 
     * @return
     */
    Boolean deleteMenu(String id, String appId);

    /**
     * 根据角色code查询菜单信息
     * @param roleCode
     * @return
     */
    List<SysMenuInfo>findMenuListByRoleCode(String roleCode, String appId);

    /**
     * 根据角色codes查询菜单列表
     * @param roleCodes
     * @return
     */
    Set<SysMenuInfo> findMenuListByRoleCodes(List<String> roleCodes, String appId);
    
    /**
     * 根据角色codes查询菜单树形
     * @param roleCodes
     * @param appId 
     * @return
     */
    List<SysMenuTree> getMenuTreeByRoleCodes(List<String> roleCodes, String appId);

    /**
     * 查询所有的菜单
     * @param appId 
     * @return
     */
    List<SysMenuTree> getAllMenuTree(String appId);

    /**
     * 查询所有的菜单
     * @param appId 
     * @return
     */
    List<TreeNode> getAllMenuTreeNode(String appId);

	List<SysMenuInfo> listAllMenuInfo(String appId);

}
