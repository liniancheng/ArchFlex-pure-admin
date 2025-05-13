package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.model.vo.SysRoleVo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.query.SysRoleInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
public interface SysRoleInfoMapper extends BaseMapper<SysRoleInfo> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    IPage<SysRoleInfo> pageByQuery(SysRoleInfoQuery query);
    
    /**
     * 根据角色名查角色个数
     * @param roleName
     * @return
     */
    Integer findRoleByName(@Param("roleName")String roleName, @Param("appId")String appId);

    /**
     * 根据角色代码查角色个数
     * @param roleCode
     * @param appId
     * @return
     */
    Integer findRoleByCode(@Param("roleCode") String roleCode, @Param("appId")String appId);
    
    
    /**
     * 根据用户角色关系查角色信息
     * @return
     */
    List<SysRoleInfoQuery> findRoleByUserRel(@Param("appId")String appId);

	List<SysRoleVo> queryRolesByAppIdAndRoleCodes(@Param("appId") String appId, @Param("roleCodes") List<String> roleCodes);

	List<SysRoleVo> queryExistRolesByRoleCodes(@Param("appId") String appId, @Param("roleCodes") List<String> roleCodes);

	List<SysRoleVo> queryRoleVos(@Param("appId") String appId);
}
