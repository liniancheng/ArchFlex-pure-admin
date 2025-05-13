package com.adtec.rdc.base.dict.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.vo.SysDictItemVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.dict.model.po.SysDictItem;
import com.adtec.rdc.base.dict.model.query.SysDictItemQuery;
import com.adtec.rdc.base.dict.service.SysDictItemService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections4.CollectionUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
@RestController
@RequestMapping("/item")
@Api(value = "字典明细controller", tags = {"字典明细操作接口"})
public class SysDictItemController {
	private static final String FUNC_NAME = "字典明细功能";

    @Autowired
    private SysDictItemService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加字典明细")
    @ApiOperation(value = "添加字典明细", notes = "字典明细信息", httpMethod = "POST")
    @ApiImplicitParam(name = "item", value = "字典明细信息", required = true, dataType = "SysDictItem")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysDictItem item){
        return new ApiResult<>(service.save(item));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改字典明细")
    @ApiOperation(value = "修改字典明细", notes = "字典明细信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "item", value = "字典明细信息", required = true, dataType = "SysDictItem")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysDictItem item){
        return new ApiResult<>(service.updateById(item));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除字典明细")
    @ApiOperation(value = "删除字典明细", notes = "删除字典明细信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "字典明细id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询字典明细信息")
    @ApiOperation(value = "查询字典明细信息", notes = "通过主键查询字典明细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典明细id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysDictItem> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "字典明细信息分页查询", notes = "字典明细信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "字典明细信息查询类", required = false, dataType = "SysDictItemQuery")
    @GetMapping("/page")
    public ApiResult<SysDictItemQuery> pageByQuery(SysDictItemQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }

    @ApiOperation(value = "查询指标系统规则", notes = "通过主键查询指标系统规则", httpMethod = "GET")
    @ApiImplicitParam(name = "dictCode", value = "数据字典dictCode", required = true, dataType = "string")
    @GetMapping("/list/{dictType}")
    public ApiResult<List<SysDictItem>> getByDictType(@PathVariable("dictType") String dictType){
        return new ApiResult<>(service.getByDictCode(dictType));
    }

    @GetMapping("/getByDictCode/{dictCode}")
    List<SysDictItemVo> getByDictCode(@PathVariable(value = "dictCode") String dictCode){
        List<SysDictItem> sysDictItems = service.getByDictCode(dictCode);
        List<SysDictItemVo> list = getSysDictItemVos(sysDictItems);
        return list;
    }

    public List<SysDictItemVo> getSysDictItemVos(List<SysDictItem> sysDictItems) {
        List<SysDictItemVo> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysDictItems)) {
            for (SysDictItem sysDictItem : sysDictItems) {
                SysDictItemVo vo = new SysDictItemVo();
                if(sysDictItem != null) {
                    BeanUtils.copyProperties(sysDictItem, vo);
                }
                list.add(vo);
            }
        }
        return list;
    }

    @PostMapping("/selectList")
    List<SysDictItemVo> selectList(@RequestBody List<String> sysVals){
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SysDictItem::getItemValue, sysVals);
        List<SysDictItem> sysDictItems = service.list(queryWrapper);
        List<SysDictItemVo> list = getSysDictItemVos(sysDictItems);
        return list;
    }
}
