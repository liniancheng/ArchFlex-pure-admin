package com.littlelee.base.common.base.controller;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.util.ApiResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author: littlelee
 * @date 2019-01-23 10:24
 */
@Tag(name = "BaseController", description = "基础增删改查接口")
public class BaseController<K extends BaseService<T>, T, P extends Serializable> {

    @Autowired
    private K baseService;

    @Operation(summary = "添加", method = "POST")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody T t) {
        return new ApiResult<>(baseService.save(t));
    }

    @Operation(summary = "修改", method = "PUT")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody T t) {
        return new ApiResult<>(baseService.updateById(t));
    }

    @Operation(summary = "删除", method = "DELETE")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "主键ID") @PathVariable("id") P id) {
        return new ApiResult<>(baseService.removeById(id));
    }

    @Operation(summary = "主键查询", method = "GET")
    @GetMapping("/{id}")
    public ApiResult<T> getById(
            @Parameter(description = "主键ID") @PathVariable("id") String id) {
        return new ApiResult<>(baseService.getById(id));
    }

    @Operation(summary = "分页查询", method = "GET")
    @GetMapping("/page")
    public ApiResult<IPage<T>> pageByQuery(IPage<T> t) {
        return new ApiResult<>(baseService.pageByQuery(t));
    }

    @Operation(summary = "查询所有信息", method = "GET")
    @GetMapping
    public ApiResult<Collection<T>> selectAll() {
        return new ApiResult<>(baseService.listByMap(new HashMap<>()));
    }
}