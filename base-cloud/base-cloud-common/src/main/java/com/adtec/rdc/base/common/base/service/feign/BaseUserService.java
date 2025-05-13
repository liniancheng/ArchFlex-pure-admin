package com.adtec.rdc.base.common.base.service.feign;

import java.util.List;
import java.util.concurrent.Future;

import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.common.model.vo.SysDictItemVo;
import com.adtec.rdc.base.common.model.vo.SysResInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.adtec.rdc.base.common.model.bo.StatisticalInfo;
import com.adtec.rdc.base.common.model.bo.SysOperlog;

@FeignClient(name = "base-cloud-user-service", contextId = "base-cloud-common-base-user")
public interface BaseUserService {
	@PostMapping("/log/save")
	Future<Boolean> saveOperLog(@RequestBody SysOperlog operLog);

	@PostMapping("/statisticalInfo/save")
	Future<Boolean> saveStatistical(@RequestBody StatisticalInfo statisticalInfo);

	@PostMapping("/branch/selectBatchByIds")
	List<SysBranchInfoVo> selectBatchByIds(@RequestBody List<String> orgVals);
	@GetMapping("/branch/selectById/{orgVal}")
	SysBranchInfoVo selectById(@PathVariable(value = "orgVal") String orgVal);

	@GetMapping("/item/getByDictCode/{dictCode}")
	List<SysDictItemVo> getByDictCode(@PathVariable(value = "dictCode") String dictCode);
	@PostMapping("/item/selectList")
	List<SysDictItemVo> selectList(@RequestBody List<String> sysVals);

	@GetMapping("/resall/selectById/{resVal}")
	SysResInfoVo queryById(@PathVariable(value = "resVal") String resVal);
}
