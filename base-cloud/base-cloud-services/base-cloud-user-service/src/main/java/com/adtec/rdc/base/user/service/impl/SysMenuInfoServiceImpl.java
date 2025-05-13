package com.adtec.rdc.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.enums.DataStatusEnum;
import com.adtec.rdc.base.common.enums.ResourceTypeEnum;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.enums.MenuCodeEnum;
import com.adtec.rdc.base.user.mapper.SysMenuInfoMapper;
import com.adtec.rdc.base.user.model.bo.SysMenuTree;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.service.SysMenuInfoService;
import com.adtec.rdc.base.user.util.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author: JTao
 * @date: 2018/10/17 14:57
 * @description: 系统资源服务实现类
 */
@Service
public class SysMenuInfoServiceImpl extends ServiceImpl<SysMenuInfoMapper, SysMenuInfo> implements SysMenuInfoService {

    @Autowired
    private SysMenuInfoMapper sysMenuInfoMapper;
    
    @Override
	public boolean save(SysMenuInfo entity) {
		if (sysMenuInfoMapper.isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.MENU_RE_NAME.getErrorCode(),
					ErrorCodeEnum.MENU_RE_NAME.getMessage());
		}
		if (MenuCodeEnum.TYPE_MENU.getCode().equals(entity.getMenuType())) {
			if (isExistMenuRouteName(entity)) {
				throw new ServiceException(ErrorCodeEnum.MENU_RE_ROUTE_NAME.getErrorCode(),
						ErrorCodeEnum.MENU_RE_ROUTE_NAME.getMessage());
			}
		} else {
			if (isExistBtnPremission(entity)) {
				throw new ServiceException(ErrorCodeEnum.MENU_RE_BTN.getErrorCode(),
						ErrorCodeEnum.MENU_RE_BTN.getMessage());
			}
		}
		if(!MenuCodeEnum.AUTH_FALSE.getCode().equals(entity.getAuthFlag())) {
			entity.setAuthFlag(MenuCodeEnum.AUTH_TRUR.getCode());
		}
		if(!MenuCodeEnum.HIDE_FALSE.getCode().equals(entity.getHideFlag())) {
			entity.setHideFlag(MenuCodeEnum.HIDE_TRUR.getCode());
		}
		if(!MenuCodeEnum.ALIVE_FALSE.getCode().equals(entity.getAliveFlag())) {
			entity.setAliveFlag(MenuCodeEnum.ALIVE_TRUE.getCode());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	public boolean updateById(SysMenuInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnum.MENU_RE_NAME.getErrorCode(),
					ErrorCodeEnum.MENU_RE_NAME.getMessage());
		}
		if (MenuCodeEnum.TYPE_MENU.getCode().equals(entity.getMenuType())) {
			if (isExistMenuRouteName(entity)) {
				throw new ServiceException(ErrorCodeEnum.MENU_RE_ROUTE_NAME.getErrorCode(),
						ErrorCodeEnum.MENU_RE_ROUTE_NAME.getMessage());
			}
		} else {
			if (isExistBtnPremission(entity)) {
				throw new ServiceException(ErrorCodeEnum.MENU_RE_BTN.getErrorCode(),
						ErrorCodeEnum.MENU_RE_BTN.getMessage());
			}
		}
		
		if(!MenuCodeEnum.AUTH_FALSE.getCode().equals(entity.getAuthFlag())) {
			entity.setAuthFlag(MenuCodeEnum.AUTH_TRUR.getCode());
		}
		if(!MenuCodeEnum.HIDE_FALSE.getCode().equals(entity.getHideFlag())) {
			entity.setHideFlag(MenuCodeEnum.HIDE_TRUR.getCode());
		}
		if(!MenuCodeEnum.ALIVE_FALSE.getCode().equals(entity.getAliveFlag())) {
			entity.setAliveFlag(MenuCodeEnum.ALIVE_TRUE.getCode());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}

    public boolean isExistName(SysMenuInfo entity) {
    	return sysMenuInfoMapper.isExistName(entity);
    }
    public boolean isExistBtnPremission(SysMenuInfo entity) {
    	return sysMenuInfoMapper.isExistBtnPremission(entity);
    }
    public boolean isExistMenuRouteName(SysMenuInfo entity) {
    	return sysMenuInfoMapper.isExistMenuRouteName(entity);
    }

    @Override
    public List<SysMenuTree> getMenuTreeByRoleCodes(List<String> roleCodes, String appId) {
        // 1、首选获取所有角色的资源集合
        Set<SysMenuInfo> sysMenus = findMenuListByRoleCodes(roleCodes, appId);
        // 2、找出类型为菜单类型的 然后排序
        List<SysMenuInfo> newMenus = sysMenus.stream()
                .filter(sysMenu -> ResourceTypeEnum.MENU.getCode().equals(sysMenu.getMenuType()))
                .peek(sysMenu -> {
                    if (StringUtils.isNotBlank(sysMenu.getMenuRouteName())) {
                        sysMenu.setMenuName(sysMenu.getMenuRouteName());
                    }
                })
                .sorted(Comparator.comparingInt(SysMenuInfo::getMenuSort))
                .collect(Collectors.toList());
        // 3、构建树
        return TreeUtil.list2Tree(newMenus, CommonConstants.TREE_ROOT);
    }

    @Override
    public Set<SysMenuInfo> findMenuListByRoleCodes(List<String> roleCodes, String appId) {
        Set<SysMenuInfo> sysMenus = new HashSet<>();
        sysMenus.addAll(sysMenuInfoMapper.findMenuListByRoleCode(roleCodes, appId));
        return sysMenus;
    }

    @Override
    public List<SysMenuTree> getAllMenuTree(String appId) {
    	QueryWrapper<SysMenuInfo> query  = new QueryWrapper<SysMenuInfo>();
    	query.lambda().eq(SysMenuInfo::getDelFlag, DataStatusEnum.NORMAL.getCode()).eq(SysMenuInfo::getAppId, appId).orderByAsc(SysMenuInfo::getMenuSort);
    	
    	List<SysMenuInfo> list = sysMenuInfoMapper.selectList(query);
    	return TreeUtil.list2Tree(list, CommonConstants.TREE_ROOT);
    }

    @Override
    public List<TreeNode> getAllMenuTreeNode(String appId) {
        QueryWrapper<SysMenuInfo> query  = new QueryWrapper<SysMenuInfo>();
        query.lambda().eq(SysMenuInfo::getDelFlag, DataStatusEnum.NORMAL.getCode()).eq(SysMenuInfo::getAppId, appId);;
        List<SysMenuInfo> list = sysMenuInfoMapper.selectList(query);
        return TreeUtil.list2TreeNode(list, CommonConstants.TREE_ROOT);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteMenu(String id, String appId) {
        // 伪删除
        QueryWrapper<SysMenuInfo> query = new QueryWrapper<>();
        query.lambda().eq(SysMenuInfo::getMenuId, id).eq(SysMenuInfo::getAppId, appId);
        SysMenuInfo sysMenuInfo = sysMenuInfoMapper.selectOne(query);
        deleteWithChildren(sysMenuInfo);

        return true;
    }

    private void deleteWithChildren(SysMenuInfo sysMenuInfo) {
        UpdateWrapper<SysMenuInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(SysMenuInfo::getParentId, sysMenuInfo.getMenuId());
        List<SysMenuInfo> children = super.list(wrapper);
        if (children != null && !children.isEmpty()) {
            children.forEach(menu -> {
                menu.setDelFlag(DataStatusEnum.LOCK.getCode());
                deleteWithChildren(menu);
            });
            super.updateBatchById(children);
        }
        sysMenuInfo.setDelFlag(DataStatusEnum.LOCK.getCode());
        super.updateById(sysMenuInfo);
    }

    @Override
     public List<SysMenuInfo> findMenuListByRoleCode(String roleCode, String appId) {
    	List<String> list = new ArrayList<String>();
    	list.add(roleCode);
        return sysMenuInfoMapper.findMenuListByRoleCode(list, appId);
    }

	@Override
	public List<SysMenuInfo> listAllMenuInfo(String appId) {
		return sysMenuInfoMapper.findAllMenusByAppId(appId);
	}

}
