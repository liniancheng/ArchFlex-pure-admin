package com.adtec.rdc.base.layout.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;
import com.adtec.rdc.base.layout.service.SysLayoutItemRelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengchf
 * @date 2020-08-25 15:08:26
 */
@RestController
@RequestMapping("/layoutItemRel")
@Api(value = "布局数据项关系controller", tags = {"布局数据项关系操作接口"})
public class SysLayoutItemRelController extends BaseHttpController{
	private static final String FUNC_NAME = "布局数据项关系功能";

    @Autowired
    private SysLayoutItemRelService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "批量添加布局数据项关系")
    @ApiOperation(value = "批量添加布局数据项关系", notes = "布局数据项关系信息", httpMethod = "POST")
    @ApiImplicitParam(name = "listRels", value = "布局数据项关系信息", required = true, dataType = "list")
    @PostMapping("/saveLists/{layId}")
    public ApiResult<Boolean> saveLists(@RequestBody List<SysLayoutItemRel> listRels, @PathVariable("layId") String layId){
        return new ApiResult<>(service.saveLists(listRels, layId));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改布局数据项关系")
    @ApiOperation(value = "修改布局数据项关系", notes = "布局数据项关系信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "id", value = "布局id", required = true, dataType = "string")
    @GetMapping("/listRels/{id}")
    public ApiResult<List<SysLayoutItemRel>> listRels(@PathVariable("id") String id){
        return new ApiResult<>(service.listRels(id, UserUtil.getAppId(request)));
    }
}
