package com.adtec.rdc.base.user.service.impl;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.constants.PubCodeConstants;
import com.adtec.rdc.base.common.constants.SecurityConstants;
import com.adtec.rdc.base.common.constants.UserConstants;
import com.adtec.rdc.base.common.easyexcel.EasyExcelListener;
import com.adtec.rdc.base.common.enums.BranchEnums;
import com.adtec.rdc.base.common.enums.EmailMessageChannnelEnum;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.email.MailSendBean;
import com.adtec.rdc.base.common.model.bo.email.MailServerBean;
import com.adtec.rdc.base.common.model.vo.DgmpFwEmployeeInfoVo;
import com.adtec.rdc.base.common.model.vo.SysRoleVo;
import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.common.util.CryptUtils;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.resource.mapper.NoticeMailSrvInfoMapper;
import com.adtec.rdc.base.resource.mapper.NoticeMailTempInfoMapper;
import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;
import com.adtec.rdc.base.resource.utils.MailUtils;
import com.adtec.rdc.base.user.mapper.SysBranchInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleInfoMapper;
import com.adtec.rdc.base.user.mapper.SysRoleUserRelMapper;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.user.model.bo.FindPasswordType;
import com.adtec.rdc.base.user.model.bo.ForgotPwdInfo;
import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.bo.SystemConfigBean;
import com.adtec.rdc.base.user.model.bo.UserAccountInfo;
import com.adtec.rdc.base.user.model.excelModel.SysUserExcelModel;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.po.SysRoleUserRel;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.user.model.po.SysUserPwdLogInfo;
import com.adtec.rdc.base.user.model.query.SysRoleInfoQuery;
import com.adtec.rdc.base.user.model.query.SysUserPwdLogInfoQuery;
import com.adtec.rdc.base.user.model.query.SysUserVoQuery;
import com.adtec.rdc.base.user.service.SysMenuInfoService;
import com.adtec.rdc.base.user.service.SysUserInfoService;
import com.adtec.rdc.base.user.service.SysUserPwdLogInfoService;
import com.adtec.rdc.base.user.service.feign.SysEmployeeService;
import com.adtec.rdc.base.user.util.RelativeDateFormat;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements SysUserInfoService {

	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
	@Autowired
	private SysMenuInfoService sysMenuInfoService;
	@Autowired
	private SysRoleUserRelMapper sysRoleUserRelMapper;
	@Autowired
	private SysRoleInfoMapper sysRoleInfoMapper;
	@Autowired
	private SysBranchInfoMapper sysBranchInfoMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MessageQueueService messageQueueService;
	@Autowired
	private SysEmployeeService sysEmployeeService;
	@Autowired
	private SystemConfigBean systemConfigBean;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private NoticeMailSrvInfoMapper noticeMailSrvInfoMapper;
	@Autowired
	private NoticeMailTempInfoMapper noticeMailTempInfoMapper;
	@Autowired
	private SysUserPwdLogInfoService userPwdLogInfoService;

	public SysUserVo getByIdWithRoles(String id, String appId) {
		SysUserVo userVo = sysUserInfoMapper.loadUserById(id);
		List<SysRoleVo> roleVoList = userVo.getSysRoleVoList();
		List<SysRoleVo> rmList = new ArrayList<SysRoleVo>();
		for (SysRoleVo sysRoleVo : roleVoList) {
			if (!sysRoleVo.getAppId().equals(appId)) {
				rmList.add(sysRoleVo);
			}
		}
		roleVoList.removeAll(rmList);
		userVo.setSysRoleVoList(roleVoList);
		return userVo;
	}

	@Override
	public SysUserVo loadUserByUsername(String username) {
		return sysUserInfoMapper.loadUserByUsername(username);
	}

	@Override
	public SysUserVo loadUserByMobile(String mobile) {
		return sysUserInfoMapper.loadUserByMobile(mobile);
	}

	@Override
	public SysUserInfo getUserInfo(String userId, List<String> roleCodes, String appId) {
		SysUserVo userVo = sysUserInfoMapper.loadUserById(userId);
		SysUserInfo userInfo = new SysUserInfo();
		BeanUtils.copyProperties(userVo, userInfo);
		userInfo.setRoles(roleCodes);
		userInfo.setUserAvatar("");
		Set<SysMenuInfo> sysMenus = sysMenuInfoService.findMenuListByRoleCodes(roleCodes, appId);
		List<String> permissions = sysMenus.stream().map(SysMenuInfo::getButtonPermission).collect(Collectors.toList())
				.stream().filter(permission -> !Strings.isNullOrEmpty(permission)).collect(Collectors.toList());
		userInfo.setPermissions(permissions);
		List<String> roleNames = Optional.ofNullable(userVo.getSysRoleVoList()).orElse(new ArrayList<SysRoleVo>())
				.stream().map(SysRoleVo::getRoleName).collect(Collectors.toList());
		if (roleNames.size() > 0) {
			userInfo.setRoleNames(StringUtils.join(roleNames, ","));
		} else {
			userInfo.setRoleNames("访客");
		}
		if (userInfo.getLoginTime() == null) {
			userInfo.setLastLoginTime("本次登录是第一次");
		} else {
			userInfo.setLastLoginTime(RelativeDateFormat.format(userInfo.getLoginTime()));
		}
		LocalDateTime last = userPwdLogInfoService.lastModifyTime(userInfo.getLoginName());
		userInfo.setLastModifyTime(last);
		return userInfo;
	}

	@Override
	public SysUserVoQuery pageUserVoByQuery(SysUserVoQuery query) {
		query.setOptimizeCountSql(false);
		sysUserInfoMapper.pageUserVoByQuery(query);
		// 查询关联用户的角色信息
		List<SysRoleInfoQuery> roles = sysRoleInfoMapper.findRoleByUserRel(query.getAppId());
		Map<String, List<SysRoleVo>> roleMap = new HashMap<String, List<SysRoleVo>>();
		roles.forEach(role -> {
			if (role != null) {
				if (!roleMap.containsKey(role.getUserId())) {
					roleMap.put(role.getUserId(), new ArrayList<SysRoleVo>());
				}
				SysRoleVo rol = new SysRoleVo();
				rol.setRoleId(role.getRoleId());
				rol.setRoleCode(role.getRoleCode());
				rol.setRoleName(role.getRoleName());
				roleMap.get(role.getUserId()).add(rol);
			}
		});
		// 用户角色
		query.getRecords().forEach(user -> {
			if (roleMap.containsKey(user.getUserId())) {
				user.setSysRoleVoList(roleMap.get(user.getUserId()));
			}
		});
		return query;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean save(SysUserVo sysUserVo) {
		// 新增用户
		SysUserInfo sysUser = new SysUserInfo();
		BeanUtils.copyProperties(sysUserVo, sysUser);
		if (StringUtils.isBlank(sysUser.getLoginPwd())) {
			sysUser.setLoginPwd(sysUser.getLoginName());
		}
		if (StringUtils.isBlank(sysUser.getBranchNo())) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_NO_NULL);
		}
		List<SysUserInfo> listSysUser = listSysUser();
		listSysUser.stream().forEach(user -> {
			if (user.getLoginName().equals(sysUser.getLoginName())) {
				throw new ServiceException(ErrorCodeEnum.USER_RE_NAME);
			}
			if (user.getUserMobTel().equals(sysUser.getUserMobTel())) {
				throw new ServiceException(ErrorCodeEnum.USER_RE_MOBEL);
			}
			if (user.getUserEmail().equals(sysUser.getUserEmail())) {
				throw new ServiceException(ErrorCodeEnum.USER_RE_EMAIL);
			}
		});
		sysUser.setLoginPwd(passwordEncoder.encode(sysUser.getLoginPwd()));
		Object thirdPwd = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.PASSWORD.THIRD_PWD.getCode());
		boolean thirdFlag = Boolean.valueOf(PubCodeConstants.PASSWORD.THIRD_PWD.getValue());
		if (thirdPwd != null) {
			thirdFlag = Boolean.valueOf((String)thirdPwd);
		}
		if (thirdFlag) {
			sysUser.setCryptPwd(CryptUtils.getEncString(sysUser.getLoginPwd(), CryptUtils.key));
		}
		sysUser.setCreateTime(LocalDateTime.now());
		sysUser.setModifyTime(LocalDateTime.now());
		this.save(sysUser);
		sysUserVo.setUserId(sysUser.getUserId());
		// 角色用户信息维护
		bindUserWithRole(sysUserVo);

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--新增用户
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, sysUserVo);
		}

		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean saveEmploy(SysUserVo sysUserVo) {
		String[] arr = sysUserVo.getLoginName().split(",");
		List<String> ids = Arrays.asList(arr);
		List<DgmpFwEmployeeInfoVo> listEmployees = sysEmployeeService.listEmployees(ids);
		String pwdType = sysUserVo.getPassWordType();
		for (DgmpFwEmployeeInfoVo employee : listEmployees) {

			// 新增用户
			SysUserInfo sysUser = new SysUserInfo();
			BeanUtils.copyProperties(sysUserVo, sysUser);
			sysUser.setBranchNo(employee.getDeptNo());
			sysUser.setUserMobTel(employee.getEmployeeMobphone());
			sysUser.setUserName(employee.getEmployeeName());
			sysUser.setLoginName(employee.getEmployeeNo());
			sysUser.setDelFlag(CommonConstants.INIT_STAT);
			sysUser.setUserEmail(employee.getEmployeeEmail());
			/**
			 * 1 与登录名相同 2 登录名+固定密码 3 固定密码
			 */
			if (CommonConstants.IS_SAME_LOGIN_NAME.equals(pwdType)) {
				sysUser.setLoginPwd(employee.getEmployeeNo());
			}
			if (CommonConstants.LOGIN_NAME_AND_FIXD.equals(pwdType)) {
				sysUser.setLoginPwd(employee.getEmployeeNo() + sysUser.getLoginPwd());
			}
			if (CommonConstants.ONLY_FIXD.equals(pwdType)) {
				sysUser.setLoginPwd(sysUser.getLoginPwd());
			}
			if (StringUtils.isBlank(sysUser.getBranchNo())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NO_NULL);
			}
			List<SysUserInfo> listSysUser = listSysUser();
			listSysUser.stream().forEach(user -> {
				if (user.getLoginName().equals(sysUser.getLoginName())) {
					throw new ServiceException(ErrorCodeEnum.USER_RE_NAME.getErrorCode(),
							"【" + sysUser.getLoginName() + "】" + ErrorCodeEnum.USER_RE_NAME.getMessage());
				}
				if (user.getUserMobTel().equals(sysUser.getUserMobTel())) {
					throw new ServiceException(ErrorCodeEnum.USER_RE_MOBEL.getErrorCode(),
							"【" + sysUser.getUserMobTel() + "】" + ErrorCodeEnum.USER_RE_MOBEL.getMessage());
				}
			});
			sysUser.setLoginPwd(passwordEncoder.encode(sysUser.getLoginPwd()));
			Object thirdPwd = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.PASSWORD.THIRD_PWD.getCode());
			boolean thirdFlag = Boolean.valueOf(PubCodeConstants.PASSWORD.THIRD_PWD.getValue());
			if (thirdPwd != null) {
				thirdFlag = Boolean.valueOf((String)thirdPwd);
			}
			if (thirdFlag) {
				sysUser.setCryptPwd(CryptUtils.getEncString(sysUser.getLoginPwd(), CryptUtils.key));
			}
			sysUser.setCreateTime(LocalDateTime.now());
			sysUser.setModifyTime(LocalDateTime.now());
			sysUser.setUserId(UUID.generate());
			this.save(sysUser);
			sysUserVo.setUserId(sysUser.getUserId());
			// 角色用户信息维护
			bindUserWithRole(sysUserVo);

			Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
			if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
				// 发送消息--新增用户
				messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, sysUserVo);
			}
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean update(SysUserVo sysUserVo) {
		// 新增用户
		SysUserInfo sysUser = this.getById(sysUserVo.getUserId());
		if (StringUtils.isBlank(sysUserVo.getBranchNo())) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_NO_NULL);
		}
		List<SysUserInfo> listSysUser = listSysUser();
		if (listSysUser.size() > 1) {
			listSysUser.remove(sysUser);
			listSysUser.stream().forEach(user -> {
				if (user.getLoginName().equals(sysUserVo.getLoginName())) {
					throw new ServiceException(ErrorCodeEnum.USER_RE_NAME);
				}
				if (user.getUserMobTel().equals(sysUserVo.getUserMobTel())) {
					throw new ServiceException(ErrorCodeEnum.USER_RE_MOBEL);
				}
				if (!StringUtils.isEmpty(user.getUserEmail())) {
					if (user.getUserEmail().equals(sysUserVo.getUserEmail())) {
						throw new ServiceException(ErrorCodeEnum.USER_RE_EMAIL);
					}
				}
			});
		}
		BeanUtils.copyProperties(sysUserVo, sysUser, "createTime", "modifyTime");
		sysUser.setModifyTime(LocalDateTime.now());
		this.updateById(sysUser);
		// 删除原来的角色用户绑定信息
		deleteUserWithRole(sysUserVo.getUserId(), sysUserVo.getAppId());
		// 角色用户信息维护
		bindUserWithRole(sysUserVo);

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--更新用户
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, sysUserVo);
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean delete(String userId) {
		SysUserVo userVo = sysUserInfoMapper.loadUserById(userId);
		this.removeById(userId);
		// 删除原来的角色用户绑定信息
		deleteUserWithRole(userId, null);

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--维护用户信息
			userVo.setDelFlag("1");
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, userVo);
		}
		return Boolean.TRUE;
	}

	/**
	 * 绑定用户与角色信息
	 * 
	 * @param sysUserVo
	 */
	private void bindUserWithRole(SysUserVo sysUserVo) {
		Optional.ofNullable(sysUserVo).map(vo -> vo.getSysRoleVoList()).ifPresent(list -> {
			list.forEach(role -> {
				SysRoleUserRel rel = new SysRoleUserRel();
				rel.setRoleId(role.getRoleId());
				rel.setUserId(sysUserVo.getUserId());
				sysRoleUserRelMapper.insert(rel);
			});
		});
	}

	/**
	 * 删除用户与角色信息
	 * 
	 * @param userId
	 */
	private void deleteUserWithRole(String userId, String appId) {
		QueryWrapper<SysRoleUserRel> wrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(appId)) {
			wrapper.lambda().eq(SysRoleUserRel::getUserId, userId);
		} else {
			wrapper.lambda().eq(SysRoleUserRel::getUserId, userId);
		}
		sysRoleUserRelMapper.delete(wrapper);
	}

	@Override
	public Boolean modifyLoginPwd(String loginName, String oldPwd, String newPwd) {
		// 新旧密码需要解密
		oldPwd = CryptUtils.getCBCDesEncrypt(oldPwd, null, null);
		newPwd = CryptUtils.getCBCDesEncrypt(newPwd, null, null);
		
		if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
			throw new ServiceException(ErrorCodeEnum.USER_PASS_NULL);
		}
		SysUserVo sysUserVo = Optional.ofNullable(sysUserInfoMapper.loadUserByUsername(loginName))
				.orElseThrow(() -> new ServiceException(ErrorCodeEnum.USER_NULL));
		if (!passwordEncoder.matches(oldPwd, sysUserVo.getLoginPwd())) {
			throw new ServiceException(ErrorCodeEnum.USER_OPASS_COMP);
		}
		SysUserInfo sysUser = new SysUserInfo();
		BeanUtils.copyProperties(sysUserVo, sysUser, "createTime", "modifyTime");
		sysUser.setModifyTime(LocalDateTime.now());
		updateUserPassword(sysUser, newPwd);
		return Boolean.TRUE;
	}

	@Override
	public SysUserInfo modifyUser(String userId, List<String> roleCodes, SysUserVo sysUserVo) {
		SysUserInfo sysUser = sysUserInfoMapper.selectById(userId);
		sysUser.setRoles(roleCodes);

		sysUser.setUserName(sysUserVo.getUserName());
		sysUser.setUserEmail(sysUserVo.getUserEmail());
		sysUser.setUserMobTel(sysUserVo.getUserMobTel());

		this.updateById(sysUser);
		sysUser.setUserAvatar("");
		Set<SysMenuInfo> sysMenus = sysMenuInfoService.findMenuListByRoleCodes(roleCodes, sysUserVo.getAppId());
		List<String> permissions = sysMenus.stream().map(SysMenuInfo::getButtonPermission).collect(Collectors.toList())
				.stream().filter(permission -> !Strings.isNullOrEmpty(permission)).collect(Collectors.toList());
		sysUser.setPermissions(permissions);

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--维护用户信息
			sysUserVo.setUserId(userId);
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, sysUserVo);
		}
		return sysUser;
	}

	@Override
	public List<SysUserInfo> listSysUser() {
		return sysUserInfoMapper.selectList(new QueryWrapper<>());
	}

	@Override
	public SysUserVo loadUserByEmail(String email) {
		return sysUserInfoMapper.loadUserByEmail(email);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean forgotPwd(ForgotPwdInfo forgotPwd) throws ServiceException {
		if (StringUtils.isEmpty(forgotPwd.getLoginName())) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringUtils.isEmpty(forgotPwd.getPassword()) || StringUtils.isEmpty(forgotPwd.getPassword2())) {
			throw new ServiceException("密码不能为空");
		}
		if (!forgotPwd.getPassword().equals(forgotPwd.getPassword2())) {
			throw new ServiceException("两次输入的密码不一致");
		}
		SysUserInfo user = sysUserInfoMapper.queryUserByLoginName(forgotPwd.getLoginName());
		if (user == null) {
			throw new ServiceException("用户[" + forgotPwd.getLoginName() + "]不存在");
		}
		if (ForgotPwdInfo.FORGOT_PWD_TYPE_EMAIL.equals(forgotPwd.getType())) {
			if (StringUtils.isEmpty(forgotPwd.getUserEmail())) {
				throw new ServiceException("邮箱不能为空");
			}
			if (StringUtils.isEmpty(user.getUserEmail()) || !user.getUserEmail().equals(forgotPwd.getUserEmail())) {
				throw new ServiceException("输入邮箱与用户设置邮箱不一致");
			}
			sendForgotPwdMail(user, forgotPwd);
		} else if (ForgotPwdInfo.FORGOT_PWD_TYPE_MOBILE.equals(forgotPwd.getType())) {
			if (StringUtils.isEmpty(forgotPwd.getUserMobTel())) {
				throw new ServiceException("手机不能为空");
			}
			if (StringUtils.isEmpty(user.getUserMobTel()) || !user.getUserMobTel().equals(forgotPwd.getUserMobTel())) {
				throw new ServiceException("输入手机与用户设置手机不一致");
			}
			Object code = redisTemplate.opsForValue()
					.get(SecurityConstants.REDIS_FORGOT_PWD_CODE_PREFIX + forgotPwd.getSmsCaptcha());
			if (code == null) {
				throw new ServiceException("验证码无效");
			}
			updateUserPassword(user, forgotPwd.getPassword());
		}
		return Boolean.TRUE;
	}

	private void sendForgotPwdMail(SysUserInfo user, ForgotPwdInfo forgotPwd) throws ServiceException {
		List<NoticeMailSrvInfo> mailSrvs = noticeMailSrvInfoMapper
				.queryMailSrvListByAppId(systemConfigBean.getForgetPasswordBean().getAppId());
		NoticeMailSrvInfo mailSrv = null;
		for (NoticeMailSrvInfo srv : mailSrvs) {
			if (srv.getSrvName().equals(systemConfigBean.getForgetPasswordBean().getMailSrvName())) {
				mailSrv = srv;
				break;
			}
		}
		if (mailSrv == null) {
			throw new ServiceException("未配置邮件服务器");
		}
		List<NoticeMailTempInfo> mailTemps = noticeMailTempInfoMapper
				.queryMailTempListByAppId(systemConfigBean.getForgetPasswordBean().getAppId());
		NoticeMailTempInfo mailTemp = null;
		for (NoticeMailTempInfo temp : mailTemps) {
			if (temp.getTempName().equals(systemConfigBean.getForgetPasswordBean().getMailTempName())) {
				mailTemp = temp;
				break;
			}
		}
		if (mailTemp == null) {
			throw new ServiceException("未配置邮件模板");
		}
		MailServerBean mailServer = MailUtils.getMailServer(mailSrv);
		MailSendBean mailSend = getMailSend(user, forgotPwd, mailTemp);
		EmailMessageTemplate emailMessageTemplate = new EmailMessageTemplate();
		emailMessageTemplate.setMailServer(mailServer);
		emailMessageTemplate.setMailSend(mailSend);
		emailMessageTemplate.setChannel(EmailMessageChannnelEnum.JAVA_MAIL.getCode());
		messageQueueService.convertAndSend(MqQueueNameConstant.MAIL_CODE_QUEUE, emailMessageTemplate);
	}

	private MailSendBean getMailSend(SysUserInfo user, ForgotPwdInfo forgotPwd, NoticeMailTempInfo mailTempInfo) {
		String mailCode = UUID.generate();
		MailSendBean bean = new MailSendBean();
		bean.setLoginName(user.getLoginName());
		bean.setTempId(mailTempInfo.getTempId());
		bean.setContent(mailTempInfo.getTempContent());
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("SEND_DATE", DateUtil.format(new Date(), "yyyy-MM-dd"));
		paraMap.put("MAIL_CODE", mailCode);
		bean.setParaMap(paraMap);
		bean.setTitle(mailTempInfo.getTempName());
		List<String> toMails = new ArrayList<String>(1);
		toMails.add(user.getUserEmail());
		bean.setToMails(toMails);
		// 存redis
		redisTemplate.opsForValue().set(SecurityConstants.REDIS_FORGOT_PWD_MAIL_CODE_PREFIX + mailCode,
				user.getLoginName() + "," + CryptUtils.getEncString(forgotPwd.getPassword(), CryptUtils.key),
				SecurityConstants.REDIS_FORGOT_PWD_MAIL_CODE_EXPIRE, TimeUnit.HOURS);
		return bean;
	}

	@Transactional
	private void updateUserPassword(SysUserInfo user, String password) {
		user.setModifyTime(LocalDateTime.now());
		
		List<Object> keys = new ArrayList<Object>();
		keys.add(PubCodeConstants.PASSWORD.LOCK_NUMB.getCode());
		keys.add(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		keys.add(PubCodeConstants.PASSWORD.THIRD_PWD.getCode());
		List<Object> list = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).multiGet(keys);
		// 为第三方集成提供密码
		if (list.get(2) != null && Boolean.valueOf((String)list.get(2))) {
			user.setCryptPwd(CryptUtils.getEncString(password, CryptUtils.key));
		}
		// 规则校验,前端校验吧，后端不处理
		
		// 最近n次修改不允许重复
		int numb = Integer.valueOf((String) Optional.ofNullable(list.get(0)).orElse(PubCodeConstants.PASSWORD.LOCK_NUMB.getValue()));
		if (passwordEncoder.matches(password, user.getLoginPwd())) {
			throw new ServiceException(ErrorCodeEnum.USER_RE_PWD.getErrorCode(),
					String.format(ErrorCodeEnum.USER_RE_PWD.getMessage(), numb));
		}
		user.setLoginPwd(passwordEncoder.encode(password));
		if (numb > 1) {
			SysUserPwdLogInfoQuery query = new SysUserPwdLogInfoQuery();
			query.setSize(numb - 1);
			query.setLoginName(user.getLoginName());
			query.setDesc("CREATE_TIME");
			userPwdLogInfoService.pageByQuery(query);
			for(SysUserPwdLogInfo info : query.getRecords()) {
				if (passwordEncoder.matches(password, info.getLoginPwd())) {
					throw new ServiceException(ErrorCodeEnum.USER_RE_PWD.getErrorCode(),
							String.format(ErrorCodeEnum.USER_RE_PWD.getMessage(), numb));
				}
			}
			SysUserPwdLogInfo entity = null;
			if (query.getTotal() < numb -1) {// 新增一个
				entity = new SysUserPwdLogInfo();
				entity.setLoginName(user.getLoginName()).setLoginPwd(user.getLoginPwd());
			} else { // 修改最后一个
				entity = query.getRecords().get(numb - 2);
				entity.setLoginPwd(user.getLoginPwd());
			}
			entity.setCreateTime(LocalDateTime.now());
			userPwdLogInfoService.saveOrUpdate(entity);
		}
		this.updateById(user);
		// 数据同步
		Object value = list.get(1);
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--维护用户信息
			SysUserVo sysUserVo = new SysUserVo();
			BeanUtils.copyProperties(user, sysUserVo);
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_USER_QUEUE, sysUserVo);
		}
	}

	@Override
	public UserAccountInfo getAccountInfo(String userId, List<String> roleIds) {
		UserAccountInfo accountInfo = new UserAccountInfo();
		SysUserInfo userInfo = sysUserInfoMapper.selectById(userId);
		BeanUtils.copyProperties(userInfo, accountInfo);
		if (StringUtils.isEmpty(userInfo.getBranchNo())) {
			accountInfo.setBranchName("(未设置)");
		} else {
			SysBranchTree branch = sysBranchInfoMapper.selectByBranchNoAndBranchType(userInfo.getBranchNo(), "P");
			if (branch == null) {
				accountInfo.setBranchName("(不存在)");
			} else {
				accountInfo.setBranchName(branch.getBranchName());
			}
		}
		if (roleIds.size() > 0) {
			List<SysRoleInfo> roles = sysRoleInfoMapper.selectBatchIds(roleIds);
			StringBuilder sb = new StringBuilder();
			for (SysRoleInfo role : roles) {
				sb.append(role.getRoleName()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			accountInfo.setRoleNames(sb.toString());
		} else {
			accountInfo.setRoleNames("访客");
		}
		if (userInfo.getLoginTime() == null) {
			userInfo.setLastLoginTime("本次登录是第一次");
		} else {
			userInfo.setLastLoginTime(RelativeDateFormat.format(userInfo.getLoginTime()));
		}
		return accountInfo;
	}

	@Override
	public Boolean updateAccountInfo(UserAccountInfo accountInfo) {
		boolean isChange = false;
		SysUserInfo userInfo = sysUserInfoMapper.selectById(accountInfo.getUserId());
		if (!StringUtils.isEmpty(accountInfo.getUserName())) {
			isChange = true;
			userInfo.setUserName(accountInfo.getUserName());
		}
		if (!StringUtils.isEmpty(accountInfo.getUserEmail())) {
			isChange = true;
			userInfo.setUserEmail(accountInfo.getUserEmail());
		}
		if (!StringUtils.isEmpty(accountInfo.getUserMobTel())) {
			isChange = true;
			userInfo.setUserMobTel(accountInfo.getUserMobTel());
		}
		if (isChange) {
			userInfo.setModifyTime(LocalDateTime.now());
			this.updateById(userInfo);
		}
		return Boolean.TRUE;
	}

	@Override
	public List<String> queryUserIdByUserNames(String userNames) {
		List<String> result = sysUserInfoMapper.queryUserIdByUserNames(Arrays.asList(userNames.split(",")));
		return result;
	}

	@Override
	public Map<String, String> getUserLoginNameMapUserId() {
		List<SysUserInfo> listSysUser = listSysUser();
		Map<String, String> map = new HashMap<>();
		listSysUser.stream().forEach(user -> {
			map.put(user.getLoginName(), user.getUserId());
		});
		return map;
	}

	@Override
	public List<SysUserVo> getAllUsers() {
		List<SysUserVo> userVos = new ArrayList<>();
		List<SysUserInfo> listSysUser = listSysUser();
		for (SysUserInfo sysUserInfo : listSysUser) {
			SysUserVo vo = new SysUserVo();
			BeanUtils.copyProperties(sysUserInfo, vo);
			userVos.add(vo);
		}
		return userVos;
	}

	@Override
	public void exportUser(String absolutePath, String fileName) {

	}

	/**
	 * 批量导入用户
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String importUser(InputStream inputStream, String type, String appId) {
		EasyExcelListener<SysUserExcelModel> listener = new EasyExcelListener<SysUserExcelModel>();
		try {
			EasyExcel.read(inputStream, SysUserExcelModel.class, listener).sheet().doRead();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, SysUserInfo> userMap = getUserMap(appId);
		Map<String, SysRoleVo> roleMap = getRoleMap(appId);
		List<String> branchNos = new ArrayList<String>();
		Map<String, String> branchMap = getBranchMap(appId, branchNos);

		List<SysUserExcelModel> list = listener.getList();
		if (list.size() == 0) {
			return "未发现导入数据";
		}
		List<SysUserInfo> insertUsers = new ArrayList<SysUserInfo>(list.size());
		List<SysUserInfo> updateUsers = new ArrayList<SysUserInfo>(list.size());
		int rowNo = 1;
		StringBuilder sb = new StringBuilder();
		for (SysUserExcelModel userModel : list) {
			SysUserInfo user = null;
			if (userMap.containsKey(userModel.getLoginName())) {
				if ("exit".equals(type)) {
					sb.append("第[").append(rowNo).append("]行，用户[").append(userModel.getLoginName()).append("]已存在");
					break;
				} else if ("skip".equals(type)) {
					sb.append("第[").append(rowNo).append("]行，用户[").append(userModel.getLoginName())
							.append("]已存在, 跳过导入\n");
					continue;
				} else if ("update".equals(type)) {
					user = userMap.get(userModel.getLoginName());
				}
			} else {
				user = new SysUserInfo();
			}
			boolean valid = true;
			BeanUtils.copyProperties(userModel, user);
			// 检查角色
			if (!StringUtils.isEmpty(userModel.getRoleNames())) {
				String[] arrayRoleNames = userModel.getRoleNames().split(",");
				for (String roleName : arrayRoleNames) {
					if (roleMap.containsKey(roleName)) {
						if (user.getRoles() == null) {
							user.setRoles(new ArrayList<String>());
						}
						user.getRoles().add(roleMap.get(roleName).getRoleId());
					} else {
						valid = false;
						sb.append("第[").append(rowNo).append("]行，角色[").append(roleName).append("]不存在\n");
					}
				}
			}
			// 检查机构
			if (StringUtils.isEmpty(userModel.getBranchName())) {
				valid = false;
				sb.append("第[").append(rowNo).append("]行，机构为空\n");
			} else {
				if (branchNos.contains(userModel.getBranchName())) {
					user.setBranchNo(userModel.getBranchName());
				} else if (branchMap.containsKey(userModel.getBranchName())) {
					user.setBranchNo(branchMap.get(userModel.getBranchName()));
				} else {
					valid = false;
					sb.append("第[").append(rowNo).append("]行，机构[").append(userModel.getBranchName()).append("]不存在\n");
				}
			}
			// 检核状态
			if (StringUtils.isEmpty(userModel.getStatusName())) {
				valid = false;
				sb.append("第[").append(rowNo).append("]行，用户状态为空\n");
			} else if ("有效".equals(userModel.getStatusName())) {
				user.setDelFlag("0");
			} else if ("无效".equals(userModel.getStatusName())) {
				user.setDelFlag("1");
			} else if ("锁定".equals(userModel.getStatusName())) {
				user.setDelFlag("9");
			} else {
				valid = false;
				sb.append("第[").append(rowNo).append("]行，用户状态不正确\n");
			}
			if (valid) {
				if (user.getUserId() == null) {
					user.setUserId(UUID.generate());
					insertUsers.add(user);
				} else {
					updateUsers.add(user);
				}
			}
			rowNo++;
		}
		Object thirdPwd = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.PASSWORD.THIRD_PWD.getCode());
		boolean thirdFlag = Boolean.valueOf(PubCodeConstants.PASSWORD.THIRD_PWD.getValue());
		if (thirdPwd != null) {
			thirdFlag = Boolean.valueOf((String)thirdPwd);
		}
		if (insertUsers.size() > 0) {
			for (SysUserInfo user : insertUsers) {
				user.setLoginPwd(passwordEncoder.encode(user.getLoginName()));
				if (thirdFlag) {
					user.setCryptPwd(CryptUtils.getEncString(user.getLoginName(), CryptUtils.key));
				}
				user.setCreateTime(LocalDateTime.now());
				this.save(user);
				if (user.getRoles() != null) {
					for (String roleId : user.getRoles()) {
						SysRoleUserRel rel = new SysRoleUserRel();
						rel.setRoleId(roleId);
						rel.setUserId(user.getUserId());
						sysRoleUserRelMapper.insert(rel);
					}
				}
			}
		}
		if (updateUsers.size() > 0) {
			for (SysUserInfo user : updateUsers) {
				user.setModifyTime(LocalDateTime.now());
				this.updateById(user);
				deleteUserWithRole(user.getUserId(), appId);
				if (user.getRoles() != null) {
					for (String roleId : user.getRoles()) {
						SysRoleUserRel rel = new SysRoleUserRel();
						rel.setRoleId(roleId);
						rel.setUserId(user.getUserId());
						sysRoleUserRelMapper.insert(rel);
					}
				}
			}
		}
		sb.append("导入结果：新增[").append(insertUsers.size()).append("]，修改[").append(updateUsers.size()).append("]，跳过[")
				.append(list.size() - insertUsers.size() - updateUsers.size()).append("]");
		return sb.toString();
	}

	private Map<String, SysUserInfo> getUserMap(String appId) {
		List<SysUserInfo> allUser = sysUserInfoMapper.loadAllUserByAppId(appId);
		Map<String, SysUserInfo> userMap = new HashMap<String, SysUserInfo>(allUser.size());
		for (SysUserInfo user : allUser) {
			userMap.put(user.getLoginName(), user);
		}
		return userMap;
	}

	private Map<String, SysRoleVo> getRoleMap(String appId) {
		List<SysRoleVo> allRole = sysRoleInfoMapper.queryRoleVos(appId);
		Map<String, SysRoleVo> roleMap = new HashMap<String, SysRoleVo>(allRole.size());
		for (SysRoleVo role : allRole) {
			roleMap.put(role.getRoleName(), role);
		}
		return roleMap;
	}

	private Map<String, String> getBranchMap(String appId, List<String> branchNos) {
		List<SysBranchInfo> allBranch = sysBranchInfoMapper.loadAllBranchByAppIdAndBranchType(appId,
				BranchEnums.TYPE_P.getCode());
		Map<String, String> branchMap = new HashMap<String, String>(allBranch.size());
		for (SysBranchInfo branch : allBranch) {
			branchNos.add(branch.getBranchNo());
			branchMap.put(branch.getBranchName(), branch.getBranchNo());
		}
		return branchMap;
	}

	/**
	 * 查询找回密码方式
	 */
	@Override
	public FindPasswordType findPasswordType() {
		FindPasswordType type = new FindPasswordType();
		SysUserVo user = sysUserInfoMapper.loadUserById(UserConstants.SUPER_USER_ID);
		if (user != null) {
			type.setAdminType(true);
			type.setAdminUserEmail(user.getUserEmail());
			type.setAdminUserPhone(user.getUserMobTel());
			type.setAdminUserName(user.getUserName());
		}
		if (systemConfigBean != null && systemConfigBean.getForgetPasswordBean() != null
				&& !StringUtils.isEmpty(systemConfigBean.getForgetPasswordBean().getAppId())) {
			if (!StringUtils.isEmpty(systemConfigBean.getForgetPasswordBean().getMailSrvName())) {
				type.setMailType(true);
			}
			if (!StringUtils.isEmpty(systemConfigBean.getForgetPasswordBean().getSmsSrvName())) {
				type.setPhoneType(true);
			}
		}
		return type;
	}

	/**
	 * 通过邮件激活密码(忘记密码)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean activePassword(String mailCode) throws ServiceException {
		String redisKey = SecurityConstants.REDIS_FORGOT_PWD_MAIL_CODE_PREFIX + mailCode;
		Object code = redisTemplate.opsForValue().get(redisKey);
		if (code == null) {
			throw new ServiceException("验证码无效");
		}
		String strCode = code.toString();
		String loginName = strCode.substring(0, strCode.indexOf(","));
		SysUserInfo user = sysUserInfoMapper.queryUserByLoginName(loginName);
		if (user == null) {
			throw new ServiceException("用户不存在");
		}
		String password = strCode.substring(strCode.indexOf(",") + 1);
		password = CryptUtils.getDesString(password, CryptUtils.key);
		updateUserPassword(user, password);
		redisTemplate.delete(redisKey);
		return Boolean.TRUE;
	}

	@Override
	public void updateLoginTime(String userId) {
		SysUserInfo user = this.getById(userId);
		user.setLoginTime(new Date());
		user.setLoginTime(new Date());
		this.updateById(user);
	}

}
