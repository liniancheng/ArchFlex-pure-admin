package com.adtec.rdc.base.resource.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.resource.model.bo.MailSendTestBean;
import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;
import com.adtec.rdc.base.resource.model.query.NoticeMailSendLogQuery;
import com.adtec.rdc.base.resource.model.query.NoticeMailSrvInfoQuery;
import com.adtec.rdc.base.resource.model.query.NoticeMailTempInfoQuery;
import com.adtec.rdc.base.resource.service.NoticeMailSendLogService;
import com.adtec.rdc.base.resource.service.NoticeMailSrvInfoService;
import com.adtec.rdc.base.resource.service.NoticeMailTempInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
@RestController
@RequestMapping("/mail")
@Api(value = "邮件controller", tags = {"邮件相关操作接口"})
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
    @ApiOperation(value = "添加邮件服务器", notes = "邮件服务器信息", httpMethod = "POST")
    @ApiImplicitParam(name = "mailSrv", value = "邮件服务器信息", required = true, dataType = "SysMailSrvInfo")
    @PostMapping("/mailSrv/save")
    public ApiResult<Boolean> saveMailSrv(@RequestBody NoticeMailSrvInfo mailSrv){
    	mailSrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.save(mailSrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改邮件服务器")
    @ApiOperation(value = "修改邮件服务器", notes = "邮件服务器信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "mailSrv", value = "邮件服务器信息", required = true, dataType = "SysMailSrvInfo")
    @PutMapping("/mailSrv/update")
    public ApiResult<Boolean> updateMailSrv(@RequestBody NoticeMailSrvInfo mailSrv){
    	mailSrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.updateById(mailSrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除邮件服务器")
    @ApiOperation(value = "删除邮件服务器", notes = "删除邮件服务器信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "邮件服务器id", required = true, dataType = "string")
    @DeleteMapping("/mailSrv/delete/{id}")
    public ApiResult<Boolean> deleteMailSrv(@PathVariable("id") String id){
        return new ApiResult<>(srvInfoService.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询邮件服务器信息")
    @ApiOperation(value = "查询邮件服务器信息", notes = "通过主键查询邮件服务器信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "邮件服务器id", required = true, dataType = "string")
    @GetMapping("/mailSrv/getById/{id}")
    public ApiResult<NoticeMailSrvInfo> getMailSrvById(@PathVariable("id") String id){
        return new ApiResult<>(srvInfoService.getById(id));
    }

    @ApiOperation(value = "邮件服务器信息分页查询", notes = "邮件服务器信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "邮件服务器信息查询类", required = false, dataType = "SysMailSrvInfoQuery")
    @GetMapping("/mailSrv/page")
    public ApiResult<NoticeMailSrvInfoQuery> pageByQuery(NoticeMailSrvInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(srvInfoService.pageByQuery(query));
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "测试邮件服务器")
    @ApiOperation(value = "测试邮件服务器", notes = "测试邮件服务器", httpMethod = "POST")
    @ApiImplicitParam(name = "mailSrv", value = "邮件服务器信息", required = true, dataType = "SysMailSrvInfo")
    @PostMapping("/mailSrv/test")
    public ApiResult<String> testMailSrv(@RequestBody MailSendTestBean sendTest){
    	sendTest.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(srvInfoService.test(sendTest));
    }

    @ApiOperation(value = "邮箱日志信息分页查询", notes = "邮箱日志信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "邮箱日志信息查询类", required = false, dataType = "SysMailLogInfoQuery")
    @GetMapping("/mailLog/page")
    public ApiResult<NoticeMailSendLogQuery> pageByQuery(NoticeMailSendLogQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(logInfoService.pageByQuery(query));
    }
    
    @ApiOperation(value = "删除操作邮箱日志", notes = "删除邮箱日志信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "邮箱日志id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(logInfoService.removeById(id));
    }
    
    @ApiOperation(value = "批量删除邮箱日志", notes = "删除邮箱日志信息", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "邮箱日志ids", required = true, dataType = "list")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(@RequestBody List<String> ids){
        return new ApiResult<>(logInfoService.removeByIds(ids));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加邮件模板")
    @ApiOperation(value = "添加邮件模板", notes = "邮件模板信息", httpMethod = "POST")
    @ApiImplicitParam(name = "mailTemp", value = "邮件模板信息", required = true, dataType = "SysMailTempInfo")
    @PostMapping("/mailTemp/save")
    public ApiResult<Boolean> saveMailTemp(@RequestBody NoticeMailTempInfo mailTemp){
    	mailTemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.save(mailTemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改邮件模板")
    @ApiOperation(value = "修改邮件模板", notes = "邮件模板信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "mailTemp", value = "邮件模板信息", required = true, dataType = "SysMailTempInfo")
    @PutMapping("/mailTemp/update")
    public ApiResult<Boolean> updateMailTemp(@RequestBody NoticeMailTempInfo mailTemp){
        mailTemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.updateById(mailTemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除邮件模板")
    @ApiOperation(value = "删除邮件模板", notes = "删除邮件模板信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "邮件模板id", required = true, dataType = "string")
    @DeleteMapping("/mailTemp/delete/{id}")
    public ApiResult<Boolean> deleteMailTemp(@PathVariable("id") String id){
        return new ApiResult<>(tempInfoService.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询邮件模板信息")
    @ApiOperation(value = "查询邮件模板信息", notes = "通过主键查询邮件模板信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "邮件模板id", required = true, dataType = "string")
    @GetMapping("/mailTemp/getById/{id}")
    public ApiResult<NoticeMailTempInfo> getMailTempById(@PathVariable("id") String id){
        return new ApiResult<>(tempInfoService.getById(id));
    }

    @ApiOperation(value = "邮件模板信息分页查询", notes = "邮件模板信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "邮件模板信息查询类", required = false, dataType = "SysMailTempInfoQuery")
    @GetMapping("/mailTemp/page")
    public ApiResult<NoticeMailTempInfoQuery> pageByQuery(NoticeMailTempInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(tempInfoService.pageByQuery(query));
    }
    @ApiOperation(value = "邮件模板信息列表", notes = "邮件模板信息列表", httpMethod = "GET")
    @ApiImplicitParam(name = "list", value = "邮件模板信息列表", required = false, dataType = "SysMailTempInfo")
    @GetMapping("/list")
    public ApiResult<List<NoticeMailTempInfo>> listByQuery(){
    	return new ApiResult<>(tempInfoService.listTemp(UserUtil.getAppId(request)));
    }
}
