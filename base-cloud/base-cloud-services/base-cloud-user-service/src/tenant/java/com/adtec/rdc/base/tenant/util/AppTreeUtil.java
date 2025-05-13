package com.adtec.rdc.base.tenant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.adtec.rdc.base.tenant.model.bo.SysAppTree;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;

/**
 * 租户树工具类
 */
public class AppTreeUtil {

    /**
     * 数组转树形结构
     * 管理端 ： 业务端
     * @param apps
     * @param appRel
     * @return
     */
    public static List<SysAppTree> list2Tree(List<SysAppInfo> apps){
        // 普通对象转树节点
        List<SysAppTree> list = buildTree(apps);
        List<SysAppTree> trees = new ArrayList<>();
        for (SysAppTree tree: list) {
        	if(StringUtils.isBlank(tree.getAppRel())) {
        		trees.add(tree);
        	}
            for (SysAppTree t : list) {
                if (tree.getAppId().equals(t.getAppRel())) {
                    if (tree.getChildren() == null) {
                    	 tree.setChildren(new ArrayList<SysAppTree>());
                    }
                    tree.addChildren(t);
                }
            }
        }
        return trees;
    }

    /**
     * 业务端：管理端
     * @param apps
     * @return
     */
    public static List<SysAppTree> list2Tree2(List<SysAppInfo> apps){
        // 普通对象转树节点
        List<SysAppTree> list = buildTree(apps);
        List<SysAppTree> trees = new ArrayList<>();
        Map<String, SysAppTree> treeMap = new HashMap<String, SysAppTree>();
        Map<String, String> treeIdMap = new HashMap<String, String>();
        for (SysAppTree tree: list) {
            treeMap.put(tree.getAppId(), tree);
            treeIdMap.put(tree.getAppId(), tree.getAppRel());
        }
        for (SysAppTree tree : treeMap.values()) {
        	if(StringUtils.isNotBlank(tree.getAppRel())) {
        		tree.setChildren(new ArrayList<SysAppTree>());
        		tree.addChildren(treeMap.get(tree.getAppRel()));
        		trees.add(tree);
        	}else {
        		if(!treeIdMap.containsValue(tree.getAppId())) {
        			trees.add(tree);
        		};
        	}
		}
        return trees;
    }
    /**
     * 对象转树节点
     * @param menus
     * @return
     */
    public static List<SysAppTree> buildTree(List<SysAppInfo> apps){
        List<SysAppTree> trees = new ArrayList<>();
        apps.forEach( app->{
            trees.add(copyMenuInfoToMenuTree(app));
        });
        return trees;
    }
    
    public static SysAppTree copyMenuInfoToMenuTree(SysAppInfo app) {
    	SysAppTree tree = new SysAppTree();
        tree.setAppId(app.getAppId());
        tree.setAppLogo(app.getAppLogo());
        tree.setAppName(app.getAppName());
        tree.setAppOrder(app.getAppOrder());
        tree.setAppRel(app.getAppRel());
        tree.setAppRmk(app.getAppRmk());
        tree.setAppState(app.getAppState());
        tree.setAppUrl(app.getAppUrl());
        tree.setBrowserTitle(app.getBrowserTitle());
        return tree;
    }
    
}
