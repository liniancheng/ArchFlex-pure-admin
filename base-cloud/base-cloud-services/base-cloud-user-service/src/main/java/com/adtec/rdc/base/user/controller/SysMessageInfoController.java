package com.adtec.rdc.base.user.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.adtec.rdc.base.user.service.SysMessageInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hewei
 * @date 2020-01-13 10:24:57
 */
@RestController
@RequestMapping("/message")
@Api(value = "消息controller", tags = {"消息操作接口"})
public class SysMessageInfoController {
	private static final String FUNC_NAME = "消息功能";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysMessageInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除消息")
    @ApiOperation(value = "删除消息", notes = "删除消息信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "标记已读")
    @ApiOperation(value = "标记已读", notes = "通过主键查询消息信息，并标记已读", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysMessageInfo> markRead(@PathVariable("id") String id){
        return new ApiResult<>(service.markRead(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "批量标记已读")
    @ApiOperation(value = "批量标记已读", notes = "通过主键查询消息信息，并标记已读", httpMethod = "PUT")
    @ApiImplicitParam(name = "idList", value = "消息id列表", required = true, dataType = "list")
    @PutMapping("/markread")
    public ApiResult markRead(@RequestBody List<String> idList){
        service.markRead(idList);
        return new ApiResult<>();
    }

    @ApiOperation(value = "消息信息分页查询", notes = "消息信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "消息信息查询类", required = false, dataType = "SysMessageInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysMessageInfoQuery> pageByQuery(SysMessageInfoQuery query){
        query.setMessageRevUser(UserUtil.getUserId(request));
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
