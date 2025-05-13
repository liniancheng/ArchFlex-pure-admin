package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.common.model.vo.SysRoleVo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.query.SysRoleInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: JTao
 * @date: 2018/11/1 15:40
 */
public interface SysRoleInfoService extends IService<SysRoleInfo> {

    /**
     * 添加角色信息 带权限资源信息
     * @param role
     * @return
     */
    boolean save(SysRoleInfo role);

    /**
     * 更新角色信息 带权限资源信息
     * @param role
     * @return
     */
    boolean updateById(SysRoleInfo role);

    /**
     * 根据id删除角色信息  同时删除与其绑定的资源信息
     * @param roleId
     * @return
     */
    Boolean deleteById(String roleId);

    /**
     * 根据角色id查询角色信息与其绑定的资源id
     * @param roleId
     * @return
     */
    SysRoleInfo getRoleInfoWithMenuByRoleId(String roleId);

    /**
     * 分页条件查询
     * @param query
     * @return
     */
    SysRoleInfoQuery pageByQuery(SysRoleInfoQuery query);

    /**
     * 查询所有的角色
     * @return
     */
    List<SysRoleInfo> listSysRole(String appId);

	List<SysRoleVo> queryRolesByRoleCodes(String appId, String roleCodes);

	List<SysRoleVo> queryExistRolesByRoleCodes(String appId, String roleCodes);

	List<SysRoleVo> queryRoleVos(String appId);

}
