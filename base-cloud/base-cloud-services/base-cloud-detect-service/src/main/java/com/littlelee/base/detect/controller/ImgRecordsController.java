package com.littlelee.base.detect.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.mapper.ImgRecordsMapper;
import com.littlelee.base.detect.model.po.ImgRecords;
import com.littlelee.base.detect.service.ImgRecordsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imgRecords")
public class ImgRecordsController {

    private static final String FUNC_NAME = "图像检测记录功能";

    @Autowired
    private ImgRecordsMapper imgRecordsMapper;
    @Autowired
    private ImgRecordsService imgRecordsService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "分页查询图像检测记录", description = "分页查询图像检测记录", method = "GET")
    @GetMapping
    public ApiResult<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "") String search1,
                                 @RequestParam(defaultValue = "") String search3,
                                 @RequestParam(defaultValue = "") String search2) {
        LambdaQueryWrapper<ImgRecords> qw = Wrappers.<ImgRecords>lambdaQuery();
        qw.orderByDesc(ImgRecords::getStartTime);
        qw.eq(ImgRecords::getDelFlag, 0);
        if (StrUtil.isNotBlank(search)) {
            qw.like(ImgRecords::getUsername, search);
        }
        if (StrUtil.isNotBlank(search1)) {
            qw.like(ImgRecords::getKind, search1);
        }
        if (StrUtil.isNotBlank(search2)) {
            qw.like(ImgRecords::getLabel, search2);
        }
        if (StrUtil.isNotBlank(search3)) {
            qw.like(ImgRecords::getConf, search3);
        }
        Page<ImgRecords> Page = imgRecordsMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return ApiResult.success(Page);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "删除图像检测记录", description = "删除图像检测记录", method = "DELETE")
    @DeleteMapping("/{ids}")
    public ApiResult<?> delete(@PathVariable Long[] ids) {
        boolean deleted = imgRecordsService.deleteImgRecords(ids);
        if (deleted) {
            return ApiResult.success("删除成功");
        }
        return ApiResult.failed("删除失败，ID不存在");
    }

}
