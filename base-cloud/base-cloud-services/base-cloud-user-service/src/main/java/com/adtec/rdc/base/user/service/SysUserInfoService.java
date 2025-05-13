package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.user.model.bo.FindPasswordType;
import com.adtec.rdc.base.user.model.bo.ForgotPwdInfo;
import com.adtec.rdc.base.user.model.bo.UserAccountInfo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.user.model.query.SysUserVoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
public interface SysUserInfoService extends IService<SysUserInfo> {

	/**
	 * 通过ID查找用户（包括角色信息）
	 * 
	 * @param id
	 * @return
	 */
	public SysUserVo getByIdWithRoles(String id, String appId);

	/**
	 * 通过登录名查找用户
	 * 
	 * @param username
	 * @return
	 */
	SysUserVo loadUserByUsername(String username);

	/**
	 * 通过mobile查找用户
	 * 
	 * @param mobile
	 * @return
	 */
	SysUserVo loadUserByMobile(String mobile);

	/**
	 * 根据userid 与角色信息返回用户详细信息
	 * 
	 * @param userId
	 * @param roleCodes
	 * @return
	 */
	SysUserInfo getUserInfo(String userId, List<String> roleCodes, String appId);

	/**
	 * 用户信息分页查询
	 * 
	 * @param query
	 * @return
	 */
	SysUserVoQuery pageUserVoByQuery(SysUserVoQuery query);

	/**
	 * 添加用户信息（带角色）
	 * 
	 * @param sysUserVo
	 * @return
	 */
	Boolean save(SysUserVo sysUserVo);

	/**
	 * 更新用户信息（带角色）
	 * 
	 * @param sysUserVo
	 * @return
	 */
	Boolean update(SysUserVo sysUserVo);

	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 * @return
	 */
	Boolean delete(String userId);

	/**
	 * 修改用户密码
	 * 
	 * @param oldPwd:旧密码
	 * @param newPwd:新密码
	 * @return
	 */
	Boolean modifyLoginPwd(String loginName, String oldPwd, String newPwd);
	/**
	 * 	用户维护自身信息
	 * @param userId
	 * @param roles
	 * @param sysUserVo
	 * @return
	 */
	SysUserInfo modifyUser(String userId, List<String> roleCodes, SysUserVo sysUserVo);

    List<SysUserInfo> listSysUser();

    /**
	 * 通过email查找用户
	 * 
	 * @param mobile
	 * @return
	 */
	SysUserVo loadUserByEmail(String email);

	/**
	 * 找回密码：通过手机/邮箱查询用户，通过验证码修改密码
	 * @param forgetPwd	找回密码的必要信息
	 * @return
	 */
	public Boolean forgotPwd(ForgotPwdInfo forgetPwd);

	/**
	 * 获取用户账户信息
	 * @param userId
	 * @param roles
	 * @return
	 */
	public UserAccountInfo getAccountInfo(String userId, List<String> roleIds);

	/**
	 * 修改账户信息
	 * @param userAccountInfo
	 * @return
	 */
	public Boolean updateAccountInfo(UserAccountInfo userAccountInfo);

	public List<String> queryUserIdByUserNames(String userNames);
	/**
	 * 导入员工信息（带角色）
	 * 
	 * @param sysUserVo
	 * @return
	 */
	public Boolean saveEmploy(SysUserVo sysUserVo) ;

	public Map<String, String> getUserLoginNameMapUserId();

	public List<SysUserVo> getAllUsers();

	public void exportUser(String absolutePath, String fileName);

	public String importUser(InputStream inputStream, String type, String appId);

	public FindPasswordType findPasswordType();

	public Boolean activePassword(String mailCode);

	public void updateLoginTime(String userId);
}
