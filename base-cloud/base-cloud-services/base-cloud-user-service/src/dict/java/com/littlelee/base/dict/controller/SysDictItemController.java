package com.littlelee.base.dict.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.vo.SysDictItemVo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.dict.model.po.SysDictItem;
import com.littlelee.base.dict.model.query.SysDictItemQuery;
import com.littlelee.base.dict.service.SysDictItemService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlelee
 * @date 2020-06-28 07:49:50
 */
@RestController
@RequestMapping("/item")
@Tag(name = "字典明细操作接口", description = "字典明细 controller")
public class SysDictItemController {

    private static final String FUNC_NAME = "字典明细功能";

    @Autowired
    private SysDictItemService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加字典明细")
    @Operation(summary = "添加字典明细", description = "字典明细信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "字典明细信息", required = true)
            @RequestBody SysDictItem item) {
        return new ApiResult<>(service.save(item));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改字典明细")
    @Operation(summary = "修改字典明细", description = "字典明细信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "字典明细信息", required = true)
            @RequestBody SysDictItem item) {
        return new ApiResult<>(service.updateById(item));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除字典明细")
    @Operation(summary = "删除字典明细", description = "删除字典明细信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "字典明细id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询字典明细信息")
    @Operation(summary = "查询字典明细信息", description = "通过主键查询字典明细信息")
    @GetMapping("/{id}")
    public ApiResult<SysDictItem> getById(
            @Parameter(description = "字典明细id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "字典明细信息分页查询", description = "字典明细信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysDictItemQuery> pageByQuery(
            @Parameter(description = "字典明细信息查询类", required = false)
            SysDictItemQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "查询指标系统规则", description = "通过主键查询指标系统规则")
    @GetMapping("/list/{dictType}")
    public ApiResult<List<SysDictItem>> getByDictType(
            @Parameter(description = "数据字典 dictType", required = true)
            @PathVariable("dictType") String dictType) {
        return new ApiResult<>(service.getByDictCode(dictType));
    }

    @GetMapping("/getByDictCode/{dictCode}")
    @Operation(summary = "根据 dictCode 获取字典明细", description = "根据 dictCode 获取字典明细列表")
    public List<SysDictItemVo> getByDictCode(
            @Parameter(description = "字典编码", required = true)
            @PathVariable("dictCode") String dictCode) {
        List<SysDictItem> sysDictItems = service.getByDictCode(dictCode);
        return getSysDictItemVos(sysDictItems);
    }

    @PostMapping("/selectList")
    @Operation(summary = "根据值列表批量查询字典明细", description = "根据值列表批量查询字典明细")
    public List<SysDictItemVo> selectList(
            @Parameter(description = "值列表", required = true)
            @RequestBody List<String> sysVals) {
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SysDictItem::getItemValue, sysVals);
        List<SysDictItem> sysDictItems = service.list(queryWrapper);
        return getSysDictItemVos(sysDictItems);
    }

    private List<SysDictItemVo> getSysDictItemVos(List<SysDictItem> sysDictItems) {
        List<SysDictItemVo> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysDictItems)) {
            for (SysDictItem sysDictItem : sysDictItems) {
                SysDictItemVo vo = new SysDictItemVo();
                if (sysDictItem != null) {
                    BeanUtils.copyProperties(sysDictItem, vo);
                }
                list.add(vo);
            }
        }
        return list;
    }
}