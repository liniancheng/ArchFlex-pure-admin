package com.littlelee.base.dict.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.dict.model.po.SysDict;
import com.littlelee.base.dict.model.query.SysDictQuery;
import com.littlelee.base.dict.model.vo.DictModel;
import com.littlelee.base.dict.service.SysDictItemService;
import com.littlelee.base.dict.service.SysDictService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author littlelee
 * @date 2020-06-28 07:42:28
 */
@RestController
@RequestMapping("/info")
@Tag(name = "字典信息操作接口", description = "字典信息 controller")
public class SysDictController {

    private static final String FUNC_NAME = "字典信息功能";

    @Autowired
    private SysDictService service;
    @Autowired
    private SysDictItemService itemService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加字典信息")
    @Operation(summary = "添加字典信息", description = "字典信息信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "字典信息信息", required = true)
            @RequestBody SysDict info) {
        return new ApiResult<>(service.save(info));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改字典信息")
    @Operation(summary = "修改字典信息", description = "字典信息信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "字典信息信息", required = true)
            @RequestBody SysDict info) {
        return new ApiResult<>(service.updateById(info));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除字典信息")
    @Operation(summary = "删除字典信息", description = "删除字典信息信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "字典信息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询字典信息信息")
    @Operation(summary = "查询字典信息信息", description = "通过主键查询字典信息信息")
    @GetMapping("/{id}")
    public ApiResult<SysDict> getById(
            @Parameter(description = "字典信息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "字典信息信息分页查询", description = "字典信息信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysDictQuery> pageByQuery(
            @Parameter(description = "字典信息信息查询类", required = false)
            SysDictQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    /**
     * 获取字典数据
     * @param dictCode 字典code
     * @return
     */
    @Operation(summary = "根据字典查询字典明细", description = "根据字典查询字典明细")
    @GetMapping("/getDictItems/{code}")
    public ApiResult<List<DictModel>> getDictItems(
            @Parameter(description = "根据字典查询字典明细", required = false)
            @PathVariable("code") String dictCode) {
        List<DictModel> list = new ArrayList<>();
        if (dictCode.contains(",")) {
            // 关联表字典（举例：sys_user_info,user_name,user_id）
            String[] params = dictCode.split(",");
            if (params.length < 3) {
                throw new ServiceException("字典Code格式不正确！");
            }
            if (params.length == 3) {
                list = itemService.queryTableDictItemsByCode(params[0], params[1], params[2]);
            } else {
                throw new ServiceException("字典Code格式不正确！");
            }
        } else {
            // 字典表
            list = itemService.queryDictItemsByCode(dictCode);
        }
        return new ApiResult<>(list);
    }
}