package com.littlelee.base.workflow.callback;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.workflow.feign.bo.WorkflowQuery;

/**
 * 动态代理
 * @author littlelee
 *
 */
public interface FeignClientProxy {
	@RequestMapping(method = RequestMethod.POST)
	ApiResult<Boolean> callback(@RequestBody WorkflowQuery workflow);
}
