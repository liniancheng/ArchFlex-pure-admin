package com.adtec.rdc.base.dict.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.dict.model.po.SysDict;
import com.adtec.rdc.base.dict.model.query.SysDictQuery;
import com.adtec.rdc.base.dict.model.vo.DictModel;
import com.adtec.rdc.base.dict.service.SysDictItemService;
import com.adtec.rdc.base.dict.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
@RestController
@RequestMapping("/info")
@Api(value = "字典信息controller", tags = {"字典信息操作接口"})
public class SysDictController {
	private static final String FUNC_NAME = "字典信息功能";

    @Autowired
    private SysDictService service;
    @Autowired
    private SysDictItemService itemService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加字典信息")
    @ApiOperation(value = "添加字典信息", notes = "字典信息信息", httpMethod = "POST")
    @ApiImplicitParam(name = "info", value = "字典信息信息", required = true, dataType = "SysDict")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysDict info){
        return new ApiResult<>(service.save(info));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改字典信息")
    @ApiOperation(value = "修改字典信息", notes = "字典信息信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "info", value = "字典信息信息", required = true, dataType = "SysDict")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysDict info){
        return new ApiResult<>(service.updateById(info));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除字典信息")
    @ApiOperation(value = "删除字典信息", notes = "删除字典信息信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "字典信息id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询字典信息信息")
    @ApiOperation(value = "查询字典信息信息", notes = "通过主键查询字典信息信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "字典信息id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysDict> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "字典信息信息分页查询", notes = "字典信息信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "字典信息信息查询类", required = false, dataType = "SysDictQuery")
    @GetMapping("/page")
    public ApiResult<SysDictQuery> pageByQuery(SysDictQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    /**
	 * 获取字典数据
	 * @param dictCode 字典code
	 * @return 
	 */
	@ApiOperation(value = "根据字典查询字典明细", notes = "根据字典查询字典明细", httpMethod = "GET")
    @ApiImplicitParam(name = "code", value = "根据字典查询字典明细", required = false, dataType = "String")
    @GetMapping("/getDictItems/{code}")
	public ApiResult<List<DictModel>> getDictItems(@PathVariable("code") String dictCode) {
		List<DictModel> list = new ArrayList<>();
		if(dictCode.indexOf(",")!=-1) {
			//关联表字典（举例：sys_user_info,user_name,user_id）
			String[] params = dictCode.split(",");
			if(params.length<3) {
				throw new ServiceException("字典Code格式不正确！");
			}
			if (params.length==3) {
				list = itemService.queryTableDictItemsByCode(params[0],params[1],params[2]);
			}else{
				throw new ServiceException("字典Code格式不正确！");
			}
		}else {
			//字典表
			list = itemService.queryDictItemsByCode(dictCode);
		}
		return new ApiResult<>(list);
	}

}
