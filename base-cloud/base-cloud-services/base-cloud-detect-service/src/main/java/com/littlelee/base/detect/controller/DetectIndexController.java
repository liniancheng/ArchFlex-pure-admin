package com.littlelee.base.detect.controller;

import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.DateUtil;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.detect.service.DetectIndexService;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.util.ApiResult;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
@RestController
@RequestMapping("/detectindex")
@Tag(name = "首页信息获取接口", description = "首页 controller")
public class DetectIndexController {
    private static final String FUNC_NAME = "检测首页功能";

    @Autowired
    private DetectIndexService service;


}
