package com.littlelee.base.knowledge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.knowledge.model.bo.KnowKnowledgeVo;
import com.littlelee.base.knowledge.service.KnowKnowledgeService;
import com.littlelee.web.antd.bo.VueTreeNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/know")
@Tag(name = "知识库查询操作接口", description = "知识库查询 controller")
public class KnowKnowledgeController extends BaseHttpController {

    private static final String FUNC_NAME = "知识库查询功能";

    @Autowired
    private KnowKnowledgeService service;

    @GetMapping("/tree")
    @Operation(summary = "知识库查询树", description = "知识库查询树")
    public List<VueTreeNode> tree(
            @Parameter(description = "知识库分类id", required = false)
            String id) {
        return service.tree(id, UserUtil.getAppId(request), UserUtil.getRoleCodes(request));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME,
            actionName = "通过主键查询知识库信息")
    @Operation(summary = "查询知识库信息", description = "通过主键查询知识库信息")
    @GetMapping("/{knowId}")
    public ApiResult<KnowKnowledgeVo> getKnowKnowledgeById(
            @Parameter(description = "知识库id", required = true)
            @PathVariable("knowId") String knowId) {
        return new ApiResult<>(service.getKnowKnowledgeById(knowId));
    }
}