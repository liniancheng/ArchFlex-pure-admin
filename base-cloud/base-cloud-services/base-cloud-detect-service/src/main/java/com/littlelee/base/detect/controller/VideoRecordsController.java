package com.littlelee.base.detect.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.mapper.VideoRecordsMapper;
import com.littlelee.base.detect.model.po.VideoRecords;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videoRecords")
public class VideoRecordsController {

    private static final String FUNC_NAME = "视频检测记录功能";

    @Autowired
    private VideoRecordsMapper videoRecordsMapper;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "视频检测记录保存", description = "视频检测记录保存", method = "POST")
    @PostMapping
    public ApiResult<?> save(@RequestBody VideoRecords videoRecords) {
        System.out.println(videoRecords);
        videoRecordsMapper.insert(videoRecords);
        return ApiResult.success("记录成功");
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "返回所有视频检测记录", description = "返回所有视频检测记录", method = "GET")
    @GetMapping("/all")
    public ApiResult<?> getAll() {
        return ApiResult.success(videoRecordsMapper.selectList(null));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "分页查询视频检测记录", description = "分页查询视频检测记录", method = "GET")
    @GetMapping
    public ApiResult<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search3,
                              @RequestParam(defaultValue = "") String search2) {
        LambdaQueryWrapper<VideoRecords> wrapper = Wrappers.<VideoRecords>lambdaQuery();
        wrapper.orderByDesc(VideoRecords::getStartTime);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(VideoRecords::getUsername, search);
        }
        if (StrUtil.isNotBlank(search1)) {
            wrapper.like(VideoRecords::getKind, search1);
        }
        if (StrUtil.isNotBlank(search2)) {
            wrapper.like(VideoRecords::getWeight, search2);
        }
        if (StrUtil.isNotBlank(search3)) {
            wrapper.like(VideoRecords::getConf, search3);
        }
        Page<VideoRecords> Page = videoRecordsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return ApiResult.success(Page);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "通过ID查询视频检测记录", description = "通过ID查询视频检测记录", method = "GET")
    @GetMapping("/{id}")
    public ApiResult<?> getById(@PathVariable int id) {
        System.out.println(id);
        return ApiResult.success(videoRecordsMapper.selectById(id));
    }

}
