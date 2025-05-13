package com.adtec.rdc.base.user.util;

import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.model.vo.SysMenuVo;
import com.adtec.rdc.base.user.model.bo.SysMenuTree;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JTao
 * @date: 2018/10/17 13:38
 * @description: 树形工具类
 */
public class TreeUtil {

    /**
     * 数组转树形结构
     * @param menus
     * @param root
     * @return
     */
    public static List<SysMenuTree> list2Tree(List<SysMenuInfo> menus, String root){
        // 普通对象转树节点
        List<SysMenuTree> list = buildTree(menus);
        List<SysMenuTree> trees = new ArrayList<>();
        for (SysMenuTree tree: list) {
            if(root.equals(tree.getParentId())) {
                trees.add(tree);
            }
            for (SysMenuTree t : list) {
                if (tree.getId().equals(t.getParentId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<SysMenuTree>());
                    }
                    tree.addChildren(t);
                }
            }
        }
        return trees;
    }

    /**
     * 数组转树形结构
     * @param menus
     * @param root
     * @return
     */
    public static List<TreeNode> list2TreeNode(List<SysMenuInfo> menus, String root){
        // 普通对象转树节点
        Map<String, List<TreeNode>> treeMap = new HashMap<>();
        List<TreeNode> trees = new ArrayList<>();
        for (SysMenuInfo menu: menus) {
            List<TreeNode> children = treeMap.get(menu.getParentId());
            if (children == null) {
                children = new ArrayList<>();
                treeMap.put(menu.getParentId(), children);
            }
            TreeNode treeNode = copyMenuInfo2TreeNode(menu);
            children.add(treeNode);
            trees.add(treeNode);
        }
        for (TreeNode tree: trees) {
            tree.setChildren(treeMap.get(tree.getKey()));
        }
        return treeMap.get(root);
    }

    /**
     * 对象转树节点
     * @param menus
     * @return
     */
    public static List<SysMenuTree> buildTree(List<SysMenuInfo> menus){
        List<SysMenuTree> trees = new ArrayList<>();
        menus.forEach( menu->{
            trees.add(copyMenuInfoToMenuTree(menu));
        });
        return trees;
    }
    
    public static SysMenuInfo copyMenuTreeToMenuInfo(SysMenuTree tree) {
    	SysMenuInfo menu = new SysMenuInfo();
    	menu.setMenuColor(tree.getColor());
    	menu.setComponentPath(tree.getComponent());
    	menu.setCreateTime(tree.getCreateTime());
    	menu.setDelFlag(tree.getDelFlag());
    	menu.setMenuIcon(tree.getIcon());
    	menu.setMenuId(tree.getId());
    	menu.setHttpMethod(tree.getMethod());
    	menu.setModifyTime(tree.getModifyTime());
    	menu.setMenuName(tree.getName());
    	menu.setParentId(tree.getParentId());
    	menu.setMenuPath(tree.getPath());
    	menu.setButtonPermission(tree.getPermission());
    	menu.setMenuSort(tree.getSort());
    	menu.setMenuType(tree.getType());
    	menu.setMenuUrl(tree.getUrl());
    	menu.setAppId(tree.getAppId());
    	menu.setAliveFlag(tree.getAliveFlag());
        return menu;
    }
    
    public static SysMenuTree copyMenuInfoToMenuTree(SysMenuInfo menu) {
    	SysMenuTree tree = new SysMenuTree();
        tree.setColor(menu.getMenuColor());
        tree.setComponent(menu.getComponentPath());
        tree.setCreateTime(menu.getCreateTime());
        tree.setDelFlag(menu.getDelFlag());
        tree.setIcon(menu.getMenuIcon());
        tree.setId(menu.getMenuId());
        tree.setMethod(menu.getHttpMethod());
        tree.setModifyTime(menu.getModifyTime());
        tree.setName(menu.getMenuName());
        tree.setRouteName(menu.getMenuRouteName());
        tree.setParentId(menu.getParentId());
        tree.setPath(menu.getMenuPath());
        tree.setPermission(menu.getButtonPermission());
        tree.setSort(menu.getMenuSort());
        tree.setType(menu.getMenuType());
        tree.setUrl(menu.getMenuUrl());
        tree.setAppId(menu.getAppId());
        tree.setAuthFlag(menu.getAuthFlag());
        tree.setHideFlag(menu.getHideFlag());
        tree.setAliveFlag(menu.getAliveFlag());
        return tree;
    }
    
    public static SysMenuVo copyMenuInfoToMenuVo(SysMenuInfo menu) {
    	SysMenuVo menuVO = new SysMenuVo();
    	menuVO.setColor(menu.getMenuColor());
    	menuVO.setComponent(menu.getComponentPath());
    	menuVO.setCreateTime(menu.getCreateTime());
    	menuVO.setDelFlag(menu.getDelFlag());
    	menuVO.setIcon(menu.getMenuIcon());
    	menuVO.setId(menu.getMenuId());
    	menuVO.setMethod(menu.getHttpMethod());
    	menuVO.setModifyTime(menu.getModifyTime());
    	menuVO.setName(menu.getMenuName());
        menuVO.setRouteName(menu.getMenuRouteName());
    	menuVO.setParentId(menu.getParentId());
    	menuVO.setPath(menu.getMenuPath());
    	menuVO.setPermission(menu.getButtonPermission());
    	menuVO.setSort(menu.getMenuSort());
    	menuVO.setType(menu.getMenuType());
    	menuVO.setUrl(menu.getMenuUrl());
    	menuVO.setAppId(menu.getAppId());
    	menuVO.setAuthFlag(menu.getAuthFlag());
    	menuVO.setHideFlag(menu.getHideFlag());
    	menuVO.setAliveFlag(menu.getAliveFlag());
        return menuVO;
    }
    
    public static SysMenuInfo copyMenuVoToMenuInfo(SysMenuVo menuVO) {
    	SysMenuInfo menu = new SysMenuInfo();
    	menu.setMenuColor(menuVO.getColor());
    	menu.setComponentPath(menuVO.getComponent());
    	menu.setCreateTime(menuVO.getCreateTime());
    	menu.setDelFlag(menuVO.getDelFlag());
    	menu.setMenuIcon(menuVO.getIcon());
    	menu.setMenuId(menuVO.getId());
    	menu.setHttpMethod(menuVO.getMethod());
    	menu.setModifyTime(menuVO.getModifyTime());
    	menu.setMenuName(menuVO.getName());
        menu.setMenuRouteName(menuVO.getRouteName());
    	menu.setParentId(menuVO.getParentId());
    	menu.setMenuPath(menuVO.getPath());
    	menu.setButtonPermission(menuVO.getPermission());
    	menu.setMenuSort(menuVO.getSort());
    	menu.setMenuType(menuVO.getType());
    	menu.setMenuUrl(menuVO.getUrl());
    	menu.setAppId(menuVO.getAppId());
    	menu.setAuthFlag(menuVO.getAuthFlag());
    	menu.setHideFlag(menuVO.getHideFlag());
    	menu.setAliveFlag(menuVO.getAliveFlag());
        return menu;
    }

    public static  TreeNode copyMenuInfo2TreeNode(SysMenuInfo menu) {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(menu.getMenuId());
        treeNode.setTitle(menu.getMenuName());
        treeNode.setValue(menu.getMenuId());
        return treeNode;
    }
}
