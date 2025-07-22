package com.littlelee.base.layout.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.layout.model.po.SysLayoutItemRel;
import com.littlelee.base.layout.service.SysLayoutItemRelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-08-25 15:08:26
 */
@RestController
@RequestMapping("/layoutItemRel")
@Tag(name = "布局数据项关系操作接口", description = "布局数据项关系 controller")
public class SysLayoutItemRelController extends BaseHttpController {

    private static final String FUNC_NAME = "布局数据项关系功能";

    @Autowired
    private SysLayoutItemRelService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "批量添加布局数据项关系")
    @Operation(summary = "批量添加布局数据项关系", description = "布局数据项关系信息")
    @PostMapping("/saveLists/{layId}")
    public ApiResult<Boolean> saveLists(
            @Parameter(description = "布局数据项关系信息列表", required = true)
            @RequestBody List<SysLayoutItemRel> listRels,
            @Parameter(description = "布局ID", required = true)
            @PathVariable("layId") String layId) {
        return new ApiResult<>(service.saveLists(listRels, layId));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改布局数据项关系")
    @Operation(summary = "查询布局数据项关系列表", description = "根据布局ID查询关联的数据项关系")
    @GetMapping("/listRels/{id}")
    public ApiResult<List<SysLayoutItemRel>> listRels(
            @Parameter(description = "布局ID", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.listRels(id, UserUtil.getAppId(request)));
    }
}