package com.adtec.rdc.base.user.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.constants.SecurityConstants;
import com.adtec.rdc.base.common.constants.UserConstants;
import com.adtec.rdc.base.common.enums.SmsMessageChannnelEnum;
import com.adtec.rdc.base.common.enums.SmsTemplateEnum;
import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.CaptchaUtil;
import com.adtec.rdc.base.common.util.NetworkUtil;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.bo.UserAccountInfo;
import com.adtec.rdc.base.user.model.bo.FindPasswordType;
import com.adtec.rdc.base.user.model.bo.ForgotPwdInfo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.user.model.query.SysUserVoQuery;
import com.adtec.rdc.base.user.service.SysUserInfoService;
import com.adtec.rdc.base.user.service.SysUserPwdLogInfoService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: JTao
 * @date: 2018/10/9 16:41
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户controller", tags = { "用户操作接口" })
public class SysUserInfoController {

	private static final String MODULE_NAME = "系统用户模块";

	@Autowired
	private SysUserInfoService sysUserService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private MessageQueueService messageQueueService;

	@Autowired
	private Producer captchaProducer;
	@Autowired
	private SysUserPwdLogInfoService userPwdLogInfoService;

	@ApiOperation(value = "获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
	@GetMapping("/info")
	public ApiResult<SysUserInfo> getInfo() {
		String userId = UserUtil.getUserId(request);
		List<String> roleCodes = UserUtil.getRoleCodes(request);
		return new ApiResult<>(sysUserService.getUserInfo(userId, roleCodes, UserUtil.getAppId(request)));
	}

	@ApiOperation(value = "根据登录名获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
	@ApiImplicitParam(name = "username", value = "登录名", required = true, dataType = "string")
	@GetMapping("/loadUserByUsername/{username}")
	public SysUserVo loadUserByUsername(@PathVariable(value = "username") String username) {
		return sysUserService.loadUserByUsername(username);
	}

	@ApiOperation(value = "根据mobile获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
	@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string")
	@GetMapping("/loadUserByMobile/{mobile}")
	public SysUserVo loadUserByMobile(@PathVariable(value = "mobile") String mobile) {
		return sysUserService.loadUserByMobile(mobile);
	}

	@ApiOperation(value = "获取用户角色信息", notes = "根据token获取用户角色信息", httpMethod = "GET")
	@GetMapping("/roles")
	public ApiResult<List<String>> getRoles() {
		return new ApiResult<>(UserUtil.getRoleCodes(request));
	}

	@ApiOperation(value = "获取用户信息 分页查询", notes = "用户信息分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "用户信息查询条件", required = false, dataType = "SysUserVoQuery")
	@GetMapping("/page")
	public ApiResult<SysUserVoQuery> pageByQuery(SysUserVoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(sysUserService.pageUserVoByQuery(query));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加用户信息")
	@ApiOperation(value = "添加用户", notes = "添加用户信息  带角色信息", httpMethod = "POST")
	@ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
	@PostMapping
	public ApiResult<Boolean> save(@RequestBody SysUserVo sysUserVo) {
		sysUserVo.setAppId(UserUtil.getAppId(request));
		String passWordType = sysUserVo.getPassWordType();
		boolean flg = false;
		if(org.apache.commons.lang.StringUtils.isEmpty(passWordType)) {
			flg = sysUserService.save(sysUserVo);
		}else {
			flg = sysUserService.saveEmploy(sysUserVo);
		}
		return new ApiResult<>(flg);
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户信息")
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息 带角色信息", httpMethod = "PUT")
	@ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
	@PutMapping
	public ApiResult<Boolean> update(@RequestBody SysUserVo sysUserVo) {
		sysUserVo.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(sysUserService.update(sysUserVo));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除用户信息")
	@ApiOperation(value = "删除用户信息", notes = "删除用户信息", httpMethod = "DELETE")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string")
	@DeleteMapping("/id/{id}")
	public ApiResult delete(@PathVariable("id") String id) {
		if (UserConstants.SUPER_USER_ID.equals(id)) {
			return ApiResult.failed("超级管理员不能删除!");
		}
		return new ApiResult<>(sysUserService.delete(id));
	}

	@ApiOperation(value = "主键查询用户信息", notes = "查询用户信息", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string")
	@GetMapping("/id/{id}")
	public ApiResult<SysUserVo> get(@PathVariable("id") String id) {
		return new ApiResult<>(sysUserService.getByIdWithRoles(id, UserUtil.getAppId(request)));
	}

	@ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", httpMethod = "GET")
	@ApiImplicitParam(name = "mobile", value = "电话号码", required = true, dataType = "string")
	@GetMapping("/mobile/{mobile}")
	public ApiResult sendMobileCode(@PathVariable("mobile") String mobile) {
		Object originCode = redisTemplate.opsForValue().get(SecurityConstants.REDIS_MOBILE_CODE_PREFIX + mobile);
		if (originCode != null) {
			log.info("手机号{}验证码{}尚未失效，请失效后再申请。", mobile, originCode);
			return ApiResult.failed("验证码尚未失效");
		}
		SysUserVo sysUserVo = sysUserService.loadUserByMobile(mobile);
		if (sysUserVo == null) {
			log.error("手机号为{} 用户不存在", mobile);
			return ApiResult.failed("手机号不存在");
		}
		String code = RandomStringUtils.random(4, false, true);
		String[] params = { code, "5" };
		SmsMessageTemplate smsMessageTemplate = new SmsMessageTemplate();
		smsMessageTemplate.setParams(params);
		smsMessageTemplate.setMobile(mobile);
		smsMessageTemplate.setSignName(SmsTemplateEnum.LOGIN_CODE.getSignName());
		smsMessageTemplate.setTemplate(SmsTemplateEnum.LOGIN_CODE.getTempalte());
		smsMessageTemplate.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());
		// 发送消息处理中心
		messageQueueService.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE, smsMessageTemplate);
		// 存redis
		redisTemplate.opsForValue().set(SecurityConstants.REDIS_MOBILE_CODE_PREFIX + mobile, Integer.valueOf(code),
				SecurityConstants.REDIS_CODE_EXPIRE, TimeUnit.SECONDS);
		return new ApiResult<>(code);
	}

	@ApiOperation(value = "生成登录验证码", notes = "生成登录验证码", httpMethod = "GET")
	@GetMapping("/captcha")
	public ApiResult getKaptcha() {
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		String codeId = NetworkUtil.getIpAddress(request);//使用客户端IP作用KEY
		// 生成验证码字符串
		String createText = captchaProducer.createText();
		String[] ret = new String[2];
		try {
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = captchaProducer.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (Exception e) {
			return ApiResult.failed("生成验证码异常");
		}
		byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		ret[0] = Base64.getEncoder().encodeToString(captchaChallengeAsJpeg);
		ret[1] = codeId;
		this.stringRedisTemplate.opsForValue().set(CaptchaUtil.getCaptchaRedisKey(codeId), createText,
				SecurityConstants.REDIS_CODE_EXPIRE, TimeUnit.SECONDS);
		return new ApiResult<>(ret);
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户登录密码")
	@ApiOperation(value = "修改用户登录密码", notes = "修改用户登录密码", httpMethod = "POST")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPwd", value = "旧密码", required = true, type = "string"),
			@ApiImplicitParam(name = "newPwd", value = "新密码", required = true, type = "string"), })
	@PostMapping("/modifyLoginPwd/{oldPwd}/{newPwd}")
	public ApiResult<Boolean> modifyLoginPwd(@PathVariable("oldPwd") String oldPwd,
			@PathVariable("newPwd") String newPwd) {
		try {
			if(sysUserService.modifyLoginPwd(UserUtil.getLoginName(request), oldPwd, newPwd)) {
				return ApiResult.success("修改成功");
			}else {
				return ApiResult.failed("修改失败");
			}
		}catch(Exception e) {
			return ApiResult.failed(e.getMessage());
		}
	}
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户登录密码")
	@ApiOperation(value = "修改用户登录密码", notes = "修改用户登录密码", httpMethod = "POST")
	@ApiImplicitParams({ @ApiImplicitParam(name = "oldPwd", value = "旧密码", required = true, type = "string"),
			@ApiImplicitParam(name = "newPwd", value = "新密码", required = true, type = "string"), })
	@PostMapping("/changeLoginPwd/{loginName}")
	public ApiResult<Boolean> changeLoginPwd(@PathVariable("loginName") String loginName, @RequestParam("oldPwd") String oldPwd,
			@RequestParam("newPwd") String newPwd) {
		try {
			if(sysUserService.modifyLoginPwd(loginName, oldPwd, newPwd)) {
				return ApiResult.success("修改成功");
			}else {
				return ApiResult.failed("修改失败");
			}
		}catch(Exception e) {
			return ApiResult.failed(e.getMessage());
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "用户维护自身信息")
	@ApiOperation(value = "用户维护自身信息", notes = "用户维护自身信息", httpMethod = "PUT")
	@PutMapping("/modifyUser")
	public ApiResult<SysUserInfo> modifyUser(@RequestBody SysUserVo sysUserVo) {
		return new ApiResult<>(
				sysUserService.modifyUser(UserUtil.getUserId(request), UserUtil.getRoleCodes(request), sysUserVo));
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询所有用户信息")
    @ApiOperation(value = "查询所有用户信息", notes = "查询用户信息", httpMethod = "GET")
    @GetMapping
    public ApiResult<List<SysUserInfo>> listUser(){
        return new ApiResult<>(sysUserService.listSysUser());
    }
	
	@ApiOperation(value = "找回密码功能，获取验证码", notes = "找回密码功能，获取验证码", httpMethod = "GET")
	@ApiImplicitParams({ @ApiImplicitParam(name = "target", value = "手机号/邮箱", required = true, dataType = "string"),
		@ApiImplicitParam(name = "type", value = "类型：mobile/email", required = true, type = "string"), })
	@GetMapping("/forgot/{type}/{target}")
	public ApiResult forgotCaptcha(@PathVariable("type") String type, @PathVariable("target") String target) {
		Object originCode = redisTemplate.opsForValue().get(SecurityConstants.REDIS_FORGOT_PWD_CODE_PREFIX + target);
		if (originCode != null) {
			log.info("验证码{}尚未失效，请失效后再申请。", target, originCode);
			return ApiResult.failed("验证码尚未失效");
		}
		SysUserVo sysUserVo = null;
		if(ForgotPwdInfo.FORGOT_PWD_TYPE_MOBILE.equals(type)) {
			sysUserVo = sysUserService.loadUserByMobile(target);
		}else if(ForgotPwdInfo.FORGOT_PWD_TYPE_EMAIL.equals(type)){
			sysUserVo = sysUserService.loadUserByEmail(target);
		}else {
			log.error("认证类型无法识别！", type);
			return ApiResult.failed("认证类型无法识别！");
		}
		if (sysUserVo == null) {
			if(ForgotPwdInfo.FORGOT_PWD_TYPE_MOBILE.equals(type)) {
				log.error("手机号为{} 用户不存在", target);
				return ApiResult.failed("手机号不存在");
			}else {
				log.error("邮箱为{} 用户不存在", target);
				return ApiResult.failed("邮箱不存在");
			}
		}
		String code = RandomStringUtils.random(4, false, true);
		String[] params = { code, "5" };
		if(ForgotPwdInfo.FORGOT_PWD_TYPE_MOBILE.equals(type)) {//手机验证码
			SmsMessageTemplate smsMessageTemplate = new SmsMessageTemplate();
			smsMessageTemplate.setParams(params);
			smsMessageTemplate.setMobile(target);
			smsMessageTemplate.setSignName(SmsTemplateEnum.LOGIN_CODE.getSignName());
			smsMessageTemplate.setTemplate(SmsTemplateEnum.LOGIN_CODE.getTempalte());
			smsMessageTemplate.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());
			// 发送消息处理中心
			messageQueueService.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE, smsMessageTemplate);
			// 存redis
			redisTemplate.opsForValue().set(SecurityConstants.REDIS_FORGOT_PWD_CODE_PREFIX + target, Integer.valueOf(code),
					SecurityConstants.REDIS_FORGOT_PWD_EXPIRE, TimeUnit.SECONDS);
		}
		return new ApiResult<>(code);
	}
	
	@ApiOperation(value = "获取账户信息", notes = "账户详细信息，附带角色信息", httpMethod = "GET")
	@GetMapping("/accountInfo")
	public ApiResult<UserAccountInfo> accountInfo() {
		String userId = UserUtil.getUserId(request);
		List<String> roleIds = UserUtil.getRoleIds(request);
		return new ApiResult<>(sysUserService.getAccountInfo(userId, roleIds));
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改账户信息")
	@ApiOperation(value = "修改账户信息", notes = "修改账户信息", httpMethod = "PUT")
	@ApiImplicitParam(name = "accountInfo", value = "账户信息", required = true, dataType = "UserAccountInfo")
	@PostMapping("/modifyAccountInfo")
	public ApiResult<Boolean> modifyAccountInfo(@RequestBody UserAccountInfo accountInfo) {
		accountInfo.setUserId(UserUtil.getUserId(request));
		return new ApiResult<>(sysUserService.updateAccountInfo(accountInfo));
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据用户名查询用户Id")
    @ApiOperation(value = "根据用户名查询用户Id", notes = "根据用户名查询用户Id", httpMethod = "GET")
	@ApiImplicitParam(name = "userNames", value = "用户名", required = true, dataType = "String")
	@GetMapping("/queryUserIdByUserNames/{userNames}")
    public List<String> queryUserIdByUserNames(@PathVariable(value = "userNames") String userNames){
        return sysUserService.queryUserIdByUserNames(userNames);
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询用户登录名和用户Id的Map")
    @ApiOperation(value = "查询用户登录名和用户Id的Map", notes = "查询用户登录名和用户Id的Map", httpMethod = "GET")
	@GetMapping("/getUserLoginNameMapUserId")
    public Map<String, String> getUserLoginNameMapUserId(){
        return sysUserService.getUserLoginNameMapUserId();
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询所有用户")
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户", httpMethod = "GET")
	@GetMapping("/getAllUsers")
    public List<SysUserVo> getAllUsers(){
        return sysUserService.getAllUsers();
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "导出用户")
    @ApiOperation(value = "导出用户", notes = "导出用户", httpMethod = "POST")
    @ApiImplicitParam(name = "type", value = "导出类型", required = true, dataType = "string")
    @PostMapping("/download/{type}")
    public void download(HttpServletResponse response, @PathVariable("type") String type) throws Exception {
		ClassPathResource classPathResource = new ClassPathResource("download/adminExcelModel/userModel.xlsx");
    	InputStream input = null;
    	if("-1".equals(type)) {
    		input = classPathResource.getInputStream();
    	} else {
    		String tmpDir = System.getProperty("java.io.tmpdir");
    		File templateFile = new File(tmpDir + "/userModel.xlsx");
    		if(!templateFile.exists()) {
    			FileUtils.copyInputStreamToFile(input, templateFile);
    		}
    		String tempOutputFileName = tmpDir + "/" + UUID.generate() + ".xlsx";
    		sysUserService.exportUser(templateFile.getAbsolutePath(), tempOutputFileName);
    		input = new FileInputStream(tempOutputFileName);
    	}
    	byte[] data = IOUtils.toByteArray(input);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"mmType.xlsx\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "导入用户")
    @ApiOperation(value = "导入用户", notes = "导入用户", httpMethod = "POST")
    @ApiImplicitParam(name = "type", value = "导入类型", required = true, dataType = "string")
    @PostMapping("/upload/{type}")
    public ApiResult<String> upload(@RequestParam("file") MultipartFile file, @PathVariable("type") String type) throws IOException {
    	String result = null;
    	if (file == null) {
    		result = "请选择正确的文件上传！";
		}else{
			try{
				result = sysUserService.importUser(file.getInputStream(), type, UserUtil.getAppId(request));
			}catch(Exception e){
				result = e.getMessage();
				e.printStackTrace();
			}
		}
    	return ApiResult.success(result);
    }
    
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户", httpMethod = "GET")
	@GetMapping("/forgot/findPasswordTypes")
    public ApiResult<FindPasswordType> findPasswordTypes(){
    	return new ApiResult<>(sysUserService.findPasswordType());
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "忘记密码")
	@ApiOperation(value = "忘记密码", notes = "忘记密码", httpMethod = "POST")
    @ApiImplicitParam(name = "forgotPwdInfo", value = "旧密码", required = true, type = "ForgotPwdInfo")
	@PostMapping("/forgot/forgetPassword")
	public ApiResult<Boolean> forgetPassword(@RequestBody ForgotPwdInfo forgotPwdInfo) {
		return new ApiResult<>(sysUserService.forgotPwd(forgotPwdInfo));
	}
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "激活密码")
	@ApiOperation(value = "激活密码", notes = "激活密码", httpMethod = "GET")
    @ApiImplicitParam(name = "mailCode", value = "验证码", required = true, dataType = "string")
	@GetMapping("/forgot/activePassword/{mailCode}")
	public ApiResult<Boolean> activePassword(@PathVariable("mailCode") String mailCode) {
		return new ApiResult<>(sysUserService.activePassword(mailCode));
	}
    
	@ApiOperation(value = "更新登录时间", notes = "更新登录时间", httpMethod = "POST")
	@PostMapping("/updateLoginTime")
	public ApiResult<Boolean> updateLoginTime() {
		sysUserService.updateLoginTime(UserUtil.getUserId(request));
		return new ApiResult<>(true);
	}
	
	@ApiOperation(value = "最近一次修改密码", notes = "最近一次修改密码", httpMethod = "GET")
	@GetMapping("/lastModifyTime/{loginName}")
	public LocalDateTime lastModifyTime(@PathVariable("loginName") String loginName) {
		return userPwdLogInfoService.lastModifyTime(loginName);
	}
}
