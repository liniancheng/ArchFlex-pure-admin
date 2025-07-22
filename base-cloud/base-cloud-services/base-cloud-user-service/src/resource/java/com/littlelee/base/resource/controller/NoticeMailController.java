package com.littlelee.base.resource.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.resource.model.bo.MailSendTestBean;
import com.littlelee.base.resource.model.po.NoticeMailSrvInfo;
import com.littlelee.base.resource.model.po.NoticeMailTempInfo;
import com.littlelee.base.resource.model.query.NoticeMailSendLogQuery;
import com.littlelee.base.resource.model.query.NoticeMailSrvInfoQuery;
import com.littlelee.base.resource.model.query.NoticeMailTempInfoQuery;
import com.littlelee.base.resource.service.NoticeMailSendLogService;
import com.littlelee.base.resource.service.NoticeMailSrvInfoService;
import com.littlelee.base.resource.service.NoticeMailTempInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
@RestController
@RequestMapping("/mail")
@Tag(name = "邮件相关操作接口", description = "邮件相关操作接口")
public class NoticeMailController {

    private static final String FUNC_NAME = "邮件相关功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NoticeMailSrvInfoService srvInfoService;
    @Autowired
    private NoticeMailSendLogService logInfoService;
    @Autowired
    private NoticeMailTempInfoService tempInfoService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加邮件服务器")
    @Operation(summary = "添加邮件服务器", description = "邮件服务器信息")
    @PostMapping("/mailSrv/save")
    public ApiResult<Boolean> saveMailSrv(
            @Parameter(description = "邮件服务器信息", required = true)
            @RequestBody NoticeMailSrvInfo mailSrv) {
        mailSrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.save(mailSrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改邮件服务器")
    @Operation(summary = "修改邮件服务器", description = "邮件服务器信息")
    @PutMapping("/mailSrv/update")
    public ApiResult<Boolean> updateMailSrv(
            @Parameter(description = "邮件服务器信息", required = true)
            @RequestBody NoticeMailSrvInfo mailSrv) {
        mailSrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.updateById(mailSrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除邮件服务器")
    @Operation(summary = "删除邮件服务器", description = "删除邮件服务器信息")
    @DeleteMapping("/mailSrv/delete/{id}")
    public ApiResult<Boolean> deleteMailSrv(
            @Parameter(description = "邮件服务器id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(srvInfoService.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询邮件服务器信息")
    @Operation(summary = "查询邮件服务器信息", description = "通过主键查询邮件服务器信息")
    @GetMapping("/mailSrv/getById/{id}")
    public ApiResult<NoticeMailSrvInfo> getMailSrvById(
            @Parameter(description = "邮件服务器id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(srvInfoService.getById(id));
    }

    @Operation(summary = "邮件服务器信息分页查询", description = "邮件服务器信息分页查询")
    @GetMapping("/mailSrv/page")
    public ApiResult<NoticeMailSrvInfoQuery> pageByQuery(NoticeMailSrvInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.pageByQuery(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "测试邮件服务器")
    @Operation(summary = "测试邮件服务器", description = "测试邮件服务器")
    @PostMapping("/mailSrv/test")
    public ApiResult<String> testMailSrv(
            @Parameter(description = "邮件服务器信息", required = true)
            @RequestBody MailSendTestBean sendTest) {
        sendTest.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(srvInfoService.test(sendTest));
    }

    @Operation(summary = "邮箱日志信息分页查询", description = "邮箱日志信息分页查询")
    @GetMapping("/mailLog/page")
    public ApiResult<NoticeMailSendLogQuery> pageByQuery(NoticeMailSendLogQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(logInfoService.pageByQuery(query));
    }

    @Operation(summary = "删除操作邮箱日志", description = "删除邮箱日志信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "邮箱日志id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(logInfoService.removeById(id));
    }

    @Operation(summary = "批量删除邮箱日志", description = "删除邮箱日志信息")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(
            @Parameter(description = "邮箱日志ids", required = true)
            @RequestBody List<String> ids) {
        return new ApiResult<>(logInfoService.removeByIds(ids));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加邮件模板")
    @Operation(summary = "添加邮件模板", description = "邮件模板信息")
    @PostMapping("/mailTemp/save")
    public ApiResult<Boolean> saveMailTemp(
            @Parameter(description = "邮件模板信息", required = true)
            @RequestBody NoticeMailTempInfo mailTemp) {
        mailTemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.save(mailTemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改邮件模板")
    @Operation(summary = "修改邮件模板", description = "邮件模板信息")
    @PutMapping("/mailTemp/update")
    public ApiResult<Boolean> updateMailTemp(
            @Parameter(description = "邮件模板信息", required = true)
            @RequestBody NoticeMailTempInfo mailTemp) {
        mailTemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.updateById(mailTemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除邮件模板")
    @Operation(summary = "删除邮件模板", description = "删除邮件模板信息")
    @DeleteMapping("/mailTemp/delete/{id}")
    public ApiResult<Boolean> deleteMailTemp(
            @Parameter(description = "邮件模板id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(tempInfoService.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询邮件模板信息")
    @Operation(summary = "查询邮件模板信息", description = "通过主键查询邮件模板信息")
    @GetMapping("/mailTemp/getById/{id}")
    public ApiResult<NoticeMailTempInfo> getMailTempById(
            @Parameter(description = "邮件模板id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(tempInfoService.getById(id));
    }

    @Operation(summary = "邮件模板信息分页查询", description = "邮件模板信息分页查询")
    @GetMapping("/mailTemp/page")
    public ApiResult<NoticeMailTempInfoQuery> pageByQuery(NoticeMailTempInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.pageByQuery(query));
    }

    @Operation(summary = "邮件模板信息列表", description = "邮件模板信息列表")
    @GetMapping("/list")
    public ApiResult<List<NoticeMailTempInfo>> listByQuery() {
        return new ApiResult<>(tempInfoService.listTemp(UserUtil.getAppId(request)));
    }
}