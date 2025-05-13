package com.adtec.rdc.base.user.mapper;

import java.util.List;

import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import feign.Param;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
public interface SysMenuInfoMapper extends BaseMapper<SysMenuInfo> {

    /**
     * 根据角色code查询菜单集合
     * @param roleCodes
     * @param appId 
     * @return
     */
    List<SysMenuInfo> findMenuListByRoleCode(@Param("roleCodes")List<String> roleCodes, @Param("appId")String appId);
    
    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenuInfo> findAllMenusByAppId(@Param("appId")String appId);
    
    /**
     * 批量插入菜单
     * @param menuList
     * @return
     */
    Integer batchInsertMenus(List<SysMenuInfo> menuList);

	List<SysMenuInfo> selectByEntity(SysMenuInfo entity);

	boolean isExistBtnPremission(SysMenuInfo entity);

	boolean isExistMenuPath(SysMenuInfo entity);

	boolean isExistMenuRouteName(SysMenuInfo entity);
    
    boolean isExistName(SysMenuInfo entity);
    
}
