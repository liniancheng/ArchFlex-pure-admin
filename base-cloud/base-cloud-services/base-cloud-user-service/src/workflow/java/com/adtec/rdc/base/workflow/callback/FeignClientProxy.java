package com.adtec.rdc.base.workflow.callback;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;

/**
 * 动态代理
 * @author JTao
 *
 */
public interface FeignClientProxy {
	@RequestMapping(method = RequestMethod.POST)
	ApiResult<Boolean> callback(@RequestBody WorkflowQuery workflow);
}
