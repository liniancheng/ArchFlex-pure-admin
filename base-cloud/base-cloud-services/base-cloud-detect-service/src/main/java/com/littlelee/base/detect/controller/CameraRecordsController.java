package com.littlelee.base.detect.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.mapper.CameraRecordsMapper;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.po.CameraRecords;
import com.littlelee.base.detect.service.CameraRecordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author littlelee
 * @date 2025-08-01 10:00:00
 */
@RestController
@RequestMapping("/cameraRecords")
@Tag(name = "摄像记录Controller", description = "摄像记录操作接口")
public class CameraRecordsController {

    private static final String FUNC_NAME = "摄像检测记录功能";

    @Autowired
    private CameraRecordsMapper cameraRecordsMapper;
    @Autowired
    private CameraRecordsService cameraRecordsService;


    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "摄像检测")
    @Operation(summary = "摄像检测记录保存", description = "摄像检测记录保存", method = "POST")
    @PostMapping
    public ApiResult<?> save(@RequestBody CameraRecords cameraRecords) {
        System.out.println(cameraRecords);
        cameraRecordsMapper.insert(cameraRecords);
        return ApiResult.success("检测记录保存成功");
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "摄像检测")
    @Operation(summary = "返回所有摄像检测记录", description = "返回所有摄像检测记录", method = "GET")
    @GetMapping("/all")
    public ApiResult<?> getAll() {
        return ApiResult.success(cameraRecordsMapper.selectList(null));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "摄像检测")
    @Operation(summary = "分页查询摄像检测记录", description = "分页查询摄像检测记录", method = "GET")
    @GetMapping
    public ApiResult<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search3,
                              @RequestParam(defaultValue = "") String search2) {
        LambdaQueryWrapper<CameraRecords> qw = Wrappers.<CameraRecords>lambdaQuery();
        qw.orderByDesc(CameraRecords::getStartTime);
        qw.eq(CameraRecords::getDelFlag, 0);
        if (StrUtil.isNotBlank(search)) {
            qw.like(CameraRecords::getUsername, search);
        }
        if (StrUtil.isNotBlank(search1)) {
            qw.like(CameraRecords::getKind, search1);
        }
        if (StrUtil.isNotBlank(search2)) {
            qw.like(CameraRecords::getWeight, search2);
        }
        if (StrUtil.isNotBlank(search3)) {
            qw.like(CameraRecords::getConf, search3);
        }
        Page<CameraRecords> Page = cameraRecordsMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return ApiResult.success(Page);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "摄像检测")
    @Operation(summary = "通过ID查询摄像检测记录", description = "通过ID查询摄像检测记录", method = "GET")
    @GetMapping("/{id}")
    public ApiResult<?> getById(@PathVariable int id) {
        System.out.println(id);
        return ApiResult.success(cameraRecordsMapper.selectById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "摄像检测")
    @Operation(summary = "删除摄像检测记录", description = "删除摄像检测记录", method = "DELETE")
    @DeleteMapping("/{ids}")
    public ApiResult<?> delete(@PathVariable Long[] ids) {
        boolean deleted = cameraRecordsService.deleteCameraRecords(ids);
        if (deleted) {
            return ApiResult.success("删除成功");
        }else {
            return ApiResult.failed("删除失败，ID不存在");
        }
    }

}
