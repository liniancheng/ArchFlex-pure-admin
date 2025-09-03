package com.littlelee.base.user.mapper;

import java.util.List;

import com.littlelee.base.user.model.po.SysMenuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.littlelee.base.user.model.vo.SysSimpleMenuVO;
import feign.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author: littlelee
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

    @Select("SELECT menu_id AS id, menu_name AS title, menu_type AS menuType, parent_id AS parentId FROM sys_menu_info WHERE del_flag = #{status} AND app_id = #{appId} ORDER BY menu_sort ASC")
    List<SysSimpleMenuVO> selectSimpleMenus(@Param("status") String status, @Param("appId") String appId);
}
