package com.adtec.rdc.base.user.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.vo.SysResInfoVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysResInfo;
import com.adtec.rdc.base.user.model.query.SysResInfoQuery;
import com.adtec.rdc.base.user.service.SysResInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author adtec
 * @date 2022-03-29 14:14:04
 */
@RestController
@RequestMapping("/resall")
@Api(value = "资源配置controller", tags = {"资源配置操作接口"})
public class SysResInfoController {
	private static final String FUNC_NAME = "资源配置功能";

    @Autowired
    private SysResInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加资源配置")
    @ApiOperation(value = "添加资源配置", notes = "资源配置信息", httpMethod = "POST")
    @ApiImplicitParam(name = "resall", value = "资源配置信息", required = true, dataType = "SysResInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysResInfo resall){
        resall.setAppId(UserUtil.getAppId(request));
        resall.setCreatedTime(new Date());
        return new ApiResult<>(service.save(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "测试资源配置")
    @ApiOperation(value = "测试资源配置", notes = "测试资源配置", httpMethod = "POST")
    @ApiImplicitParam(name = "resall", value = "测试资源配置", required = true, dataType = "SysResInfo")
    @PostMapping("/test")
    public ApiResult<Boolean> test(@RequestBody SysResInfo resall){
        return new ApiResult<>(service.test(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改资源配置")
    @ApiOperation(value = "修改资源配置", notes = "资源配置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "resall", value = "资源配置信息", required = true, dataType = "SysResInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysResInfo resall){
        resall.setModifyTime(new Date());
        return new ApiResult<>(service.updateById(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除资源配置")
    @ApiOperation(value = "删除资源配置", notes = "删除资源配置信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "资源配置id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询资源配置信息")
    @ApiOperation(value = "查询资源配置信息", notes = "通过主键查询资源配置信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "资源配置id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysResInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "资源配置信息分页查询", notes = "资源配置信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "资源配置信息查询类", required = false, dataType = "SysResInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysResInfoQuery> pageByQuery(SysResInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }

    @ApiOperation(value = "资源配置信息列表查询", notes = "资源配置信息列表查询", httpMethod = "GET")
    @GetMapping("/list")
    public ApiResult<List<SysResInfo>> list(){
        return new ApiResult<>(service.fetchList());
    }

    @GetMapping("/selectById/{resVal}")
    public SysResInfoVo queryById(@PathVariable(value = "resVal") String resVal){
        SysResInfo sysResInfo = service.getById(resVal);
        SysResInfoVo vo = new SysResInfoVo();
        if(sysResInfo != null) {
            BeanUtils.copyProperties(sysResInfo, vo);
        }
        return vo;
    }
}
