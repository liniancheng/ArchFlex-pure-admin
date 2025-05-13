package com.adtec.rdc.base.knowledge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeVo;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeService;
import com.adtec.rdc.web.antd.bo.VueTreeNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/know")
@Api(value = "知识库查询controller", tags = {"知识库查询操作接口"})
public class KnowKnowledgeController extends BaseHttpController {
	private static final String FUNC_NAME = "知识库查询功能";
	@Autowired
    private KnowKnowledgeService service;
	
	@GetMapping("/tree")
	@ApiOperation(value = "知识库查询树", notes = "知识库查询树", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "知识库分类id", required = false, dataType = "string")
	public List<VueTreeNode> tree(String id) {
		return service.tree(id, UserUtil.getAppId(request), UserUtil.getRoleCodes(request));
	}
	
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询知识库信息")
    @ApiOperation(value = "查询知识库信息", notes = "通过主键查询知识库信息", httpMethod = "GET")
    @ApiImplicitParam(name = "knowId", value = "知识库id", required = true, dataType = "string")
    @GetMapping("/{knowId}")
    public ApiResult<KnowKnowledgeVo> getKnowKnowledgeById(@PathVariable("knowId") String knowId) {
        return new ApiResult<>(service.getKnowKnowledgeById(knowId));
    }
}
