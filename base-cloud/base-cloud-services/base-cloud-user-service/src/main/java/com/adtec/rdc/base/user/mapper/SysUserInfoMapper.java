package com.adtec.rdc.base.user.mapper;

import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.user.model.query.SysUserVoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {

    /**
     * 更加Id查询用户信息（包括角色信息）
     * @param id
     * @return
     */
    SysUserVo loadUserById(String id);

    /**
     * 根据登录名查询用户信息（包括角色信息）
     * @param username 登录名
     * @return
     */
    SysUserVo loadUserByUsername(String username);

    /**
     * 根据mobile查询用户信息（包括角色信息）
     * @param mobile 手机号
     * @return
     */
    SysUserVo loadUserByMobile(String mobile);

    /**
     * 用户信息分页查询
     * @param query
     * @return
     */
    IPage<SysUserVo> pageUserVoByQuery(SysUserVoQuery query);

    /**
     * 统计
     * @param query
     * @return
     */
    Integer countUserByQuery(SysUserVoQuery query);

    /**
     * 根据email查询用户信息（包括角色信息）
     * @param email 登录名
     * @return
     */
	SysUserVo loadUserByEmail(String email);

	/**
	 * 精确查询用户(根据登录名或姓名, 返回列表size=1)
	 * @param loginName
	 * @return
	 */
	List<SysUserInfo> queryUserByLoginNameOrUserName(String loginName);

	List<String> queryUserIdByUserNames(@Param("userNames") List<String> userNames);

	List<SysUserInfo> loadAllUserByAppId(@Param("appId") String appId);

	SysUserInfo queryUserByLoginName(@Param("loginName") String loginName);
}
