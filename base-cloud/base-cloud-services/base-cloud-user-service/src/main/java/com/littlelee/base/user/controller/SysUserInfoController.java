package com.littlelee.base.user.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.service.MessageQueueService;
import com.littlelee.base.common.constants.*;
import com.littlelee.base.common.enums.SmsMessageChannnelEnum;
import com.littlelee.base.common.enums.SmsTemplateEnum;
import com.littlelee.base.common.model.vo.SysUserVo;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;
import com.littlelee.base.common.util.*;
import com.littlelee.base.user.model.bo.*;
import com.littlelee.base.user.model.po.SysUserInfo;
import com.littlelee.base.user.model.query.SysUserVoQuery;
import com.littlelee.base.user.service.SysUserInfoService;
import com.littlelee.base.user.service.SysUserPwdLogInfoService;
import com.google.code.kaptcha.Producer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: littlelee
 * @date: 2018/10/9 16:41
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户操作接口", description = "用户 controller")
public class SysUserInfoController {

	private static final String MODULE_NAME = "系统用户模块";

	@Autowired private SysUserInfoService sysUserService;
	@Autowired private HttpServletRequest request;
	@Autowired private RedisTemplate<String, Object> redisTemplate;
	@Autowired private StringRedisTemplate stringRedisTemplate;
	@Autowired private MessageQueueService messageQueueService;
	@Autowired private Producer captchaProducer;
	@Autowired private SysUserPwdLogInfoService userPwdLogInfoService;

	/* -------------------- 基础信息 -------------------- */
	@Operation(summary = "获取用户信息", description = "用户详细信息，附带角色信息，权限信息")
	@GetMapping("/info")
	public ApiResult<SysUserInfo> getInfo() {
		return new ApiResult<>(sysUserService.getUserInfo(
				UserUtil.getUserId(request),
				UserUtil.getRoleCodes(request),
				UserUtil.getAppId(request)));
	}

	@Operation(summary = "根据登录名获取用户信息", description = "用户详细信息，附带角色信息，权限信息")
	@GetMapping("/loadUserByUsername/{username}")
	public SysUserVo loadUserByUsername(@Parameter(description = "登录名", required = true) @PathVariable String username) {
		return sysUserService.loadUserByUsername(username);
	}

	@Operation(summary = "根据手机号获取用户信息", description = "用户详细信息，附带角色信息，权限信息")
	@GetMapping("/loadUserByMobile/{mobile}")
	public SysUserVo loadUserByMobile(@Parameter(description = "手机号", required = true) @PathVariable String mobile) {
		return sysUserService.loadUserByMobile(mobile);
	}

	@Operation(summary = "获取用户角色信息", description = "根据token获取用户角色信息")
	@GetMapping("/roles")
	public ApiResult<List<String>> getRoles() {
		return new ApiResult<>(UserUtil.getRoleCodes(request));
	}

	@Operation(summary = "用户信息分页查询", description = "用户信息分页查询")
	@GetMapping("/page")
	public ApiResult<SysUserVoQuery> pageByQuery(@Parameter(description = "查询条件", required = false) SysUserVoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(sysUserService.pageUserVoByQuery(query));
	}

	/* -------------------- CRUD -------------------- */
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加用户")
	@Operation(summary = "添加用户", description = "添加用户信息（含角色信息）")
	@PostMapping
	public ApiResult<Boolean> save(@Parameter(description = "用户信息", required = true) @RequestBody SysUserVo sysUserVo) {
		sysUserVo.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(StringUtils.isEmpty(sysUserVo.getPassWordType())
				? sysUserService.save(sysUserVo)
				: sysUserService.saveEmploy(sysUserVo));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户")
	@Operation(summary = "修改用户", description = "修改用户信息（含角色信息）")
	@PutMapping
	public ApiResult<Boolean> update(@Parameter(description = "用户信息", required = true) @RequestBody SysUserVo sysUserVo) {
		sysUserVo.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(sysUserService.update(sysUserVo));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除用户")
	@Operation(summary = "删除用户", description = "删除用户（超级管理员不可删除）")
	@DeleteMapping("/id/{id}")
	public ApiResult<?> delete(@Parameter(description = "用户id", required = true) @PathVariable String id) {
		return UserConstants.SUPER_USER_ID.equals(id)
				? ApiResult.failed("超级管理员不能删除!")
				: new ApiResult<>(sysUserService.delete(id));
	}

	@Operation(summary = "主键查询用户信息", description = "查询用户信息")
	@GetMapping("/id/{id}")
	public ApiResult<SysUserVo> get(@Parameter(description = "用户id", required = true) @PathVariable String id) {
		return new ApiResult<>(sysUserService.getByIdWithRoles(id, UserUtil.getAppId(request)));
	}

	/* -------------------- 验证码 -------------------- */
	@Operation(summary = "发送手机验证码", description = "发送手机验证码")
	@GetMapping("/mobile/{mobile}")
	public ApiResult<String> sendMobileCode(@Parameter(description = "手机号", required = true) @PathVariable String mobile) {
		String key = SecurityConstants.REDIS_MOBILE_CODE_PREFIX + mobile;
		if (redisTemplate.hasKey(key)) {
			return ApiResult.failed("验证码尚未失效");
		}
		SysUserVo user = sysUserService.loadUserByMobile(mobile);
		if (user == null) {
			return ApiResult.failed("手机号不存在");
		}
		String code = RandomStringUtils.randomNumeric(4);
		SmsMessageTemplate sms = new SmsMessageTemplate();
		sms.setParams(new String[]{code, "5"});
		sms.setMobile(mobile);
		sms.setSignName(SmsTemplateEnum.LOGIN_CODE.getSignName());
		sms.setTemplate(SmsTemplateEnum.LOGIN_CODE.getTempalte());
		sms.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());
		messageQueueService.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE, sms);
		redisTemplate.opsForValue().set(key, Integer.valueOf(code),
				SecurityConstants.REDIS_CODE_EXPIRE, TimeUnit.SECONDS);
		return new ApiResult<>(code);
	}

	@Operation(summary = "生成登录验证码", description = "返回 Base64 图片及 codeId")
	@GetMapping("/captcha")
	public ApiResult<String[]> getKaptcha() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String codeId = NetworkUtil.getIpAddress(request);
		String text = captchaProducer.createText();
		try {
			BufferedImage image = captchaProducer.createImage(text);
			ImageIO.write(image, "jpg", os);
		} catch (Exception e) {
			return ApiResult.failed("生成验证码异常");
		}
		String[] ret = {
				Base64.getEncoder().encodeToString(os.toByteArray()),
				codeId
		};
		stringRedisTemplate.opsForValue().set(
				CaptchaUtil.getCaptchaRedisKey(codeId), text,
				SecurityConstants.REDIS_CODE_EXPIRE, TimeUnit.SECONDS);
		return new ApiResult<>(ret);
	}

	/* -------------------- 密码 -------------------- */
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户登录密码")
	@Operation(summary = "修改用户登录密码", description = "需要旧密码验证")
	@PostMapping("/modifyLoginPwd/{oldPwd}/{newPwd}")
	public ApiResult<Boolean> modifyLoginPwd(
			@Parameter(description = "旧密码", required = true) @PathVariable String oldPwd,
			@Parameter(description = "新密码", required = true) @PathVariable String newPwd) {
		return sysUserService.modifyLoginPwd(UserUtil.getLoginName(request), oldPwd, newPwd)
				? ApiResult.success("修改成功")
				: ApiResult.failed("修改失败");
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户登录密码")
	@Operation(summary = "管理员修改指定用户密码", description = "需要旧密码验证")
	@PostMapping("/changeLoginPwd/{loginName}")
	public ApiResult<Boolean> changeLoginPwd(
			@Parameter(description = "登录名", required = true) @PathVariable String loginName,
			@Parameter(description = "旧密码", required = true) @RequestParam String oldPwd,
			@Parameter(description = "新密码", required = true) @RequestParam String newPwd) {
		return sysUserService.modifyLoginPwd(loginName, oldPwd, newPwd)
				? ApiResult.success("修改成功")
				: ApiResult.failed("修改失败");
	}

	/* -------------------- 其他接口 -------------------- */
	@Operation(summary = "用户维护自身信息", description = "用户维护自身信息")
	@PutMapping("/modifyUser")
	public ApiResult<SysUserInfo> modifyUser(@Parameter(description = "用户信息", required = true) @RequestBody SysUserVo sysUserVo) {
		return new ApiResult<>(sysUserService.modifyUser(
				UserUtil.getUserId(request),
				UserUtil.getRoleCodes(request),
				sysUserVo));
	}

	@Operation(summary = "查询所有用户", description = "查询所有用户")
	@GetMapping
	public ApiResult<List<SysUserInfo>> listUser() {
		return new ApiResult<>(sysUserService.listSysUser());
	}

	/* -------------------- 导入导出 -------------------- */
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "导出用户")
	@Operation(summary = "导出用户", description = "导出用户模板或数据")
	@PostMapping("/download/{type}")
	public void download(HttpServletResponse response,
						 @Parameter(description = "导出类型", required = true) @PathVariable String type) throws Exception {
		ClassPathResource resource = new ClassPathResource("download/adminExcelModel/userModel.xlsx");
		InputStream input = "-1".equals(type)
				? resource.getInputStream()
				: exportUserData();
		byte[] data = IOUtils.toByteArray(input);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"mmType.xlsx\"");
		response.addHeader("Content-Length", String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
		IOUtils.closeQuietly(input);
	}

	private InputStream exportUserData() throws IOException {
		String tmpDir = System.getProperty("java.io.tmpdir");
		File template = new File(tmpDir, "userModel.xlsx");
		if (!template.exists()) {
			FileUtils.copyInputStreamToFile(new ClassPathResource("download/adminExcelModel/userModel.xlsx").getInputStream(), template);
		}
		String outFile = tmpDir + "/" + UUID.generate() + ".xlsx";
		sysUserService.exportUser(template.getAbsolutePath(), outFile);
		return new FileInputStream(outFile);
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "导入用户")
	@Operation(summary = "导入用户", description = "批量导入用户")
	@PostMapping("/upload/{type}")
	public ApiResult<String> upload(
			@Parameter(description = "导入文件", required = true) @RequestParam("file") MultipartFile file,
			@Parameter(description = "导入类型", required = true) @PathVariable String type) throws IOException {
		if (file == null || file.isEmpty()) {
			return ApiResult.success("请选择正确的文件上传！");
		}
		try {
			String result = sysUserService.importUser(file.getInputStream(), type, UserUtil.getAppId(request));
			return ApiResult.success(result);
		} catch (Exception e) {
			return ApiResult.success(e.getMessage());
		}
	}

	/* -------------------- 密码找回 -------------------- */
	@Operation(summary = "找回密码类型枚举", description = "获取支持的找回密码类型")
	@GetMapping("/forgot/findPasswordTypes")
	public ApiResult<FindPasswordType> findPasswordTypes() {
		return new ApiResult<>(sysUserService.findPasswordType());
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "忘记密码")
	@Operation(summary = "忘记密码", description = "发送验证码并重置密码")
	@PostMapping("/forgot/forgetPassword")
	public ApiResult<Boolean> forgetPassword(@Parameter(description = "找回密码信息", required = true) @RequestBody ForgotPwdInfo forgotPwdInfo) {
		return new ApiResult<>(sysUserService.forgotPwd(forgotPwdInfo));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "激活密码")
	@Operation(summary = "激活密码", description = "邮箱激活密码")
	@GetMapping("/forgot/activePassword/{mailCode}")
	public ApiResult<Boolean> activePassword(@Parameter(description = "邮箱验证码", required = true) @PathVariable String mailCode) {
		return new ApiResult<>(sysUserService.activePassword(mailCode));
	}

	/* -------------------- 登录/时间 -------------------- */
	@Operation(summary = "更新登录时间", description = "更新用户最近一次登录时间")
	@PostMapping("/updateLoginTime")
	public ApiResult<Boolean> updateLoginTime() {
		sysUserService.updateLoginTime(UserUtil.getUserId(request));
		return new ApiResult<>(true);
	}

	@Operation(summary = "最近一次修改密码时间", description = "根据登录名查询最近一次修改密码时间")
	@GetMapping("/lastModifyTime/{loginName}")
	public LocalDateTime lastModifyTime(@Parameter(description = "登录名", required = true) @PathVariable String loginName) {
		return userPwdLogInfoService.lastModifyTime(loginName);
	}
}