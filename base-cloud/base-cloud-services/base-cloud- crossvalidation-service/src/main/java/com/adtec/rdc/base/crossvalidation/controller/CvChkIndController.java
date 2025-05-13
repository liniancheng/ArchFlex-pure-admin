package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndTree;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkInd;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndService;
import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.util.ApiResult;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
@RestController
@RequestMapping("/cvchkind")
@Api(value = "指标定义controller", tags = {"指标定义操作接口"})
public class CvChkIndController {
    private static final String FUNC_NAME = "指标定义功能";

    @Autowired
    private CvChkIndService service;

    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "添加指标定义")
    @ApiOperation(value = "添加指标定义", notes = "指标定义信息", httpMethod = "POST")
    @ApiImplicitParam(name = "crossvalidation", value = "指标定义信息", required = true, dataType = "CvChkInd")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody CvChkInd check) {
        String parentLevel = check.getParentLevel();
        if (StringUtils.isNotBlank(parentLevel)) {
            Integer parseInt = Integer.parseInt(parentLevel);
            parseInt = parseInt + 1;
            check.setIndLevel(parseInt.toString());
        }
        String name = UserUtil.getLoginName(request);
        check.setCreateUser(name);
        check.setCreateTime(DateUtil.format(new Date()));
        return new ApiResult<>(service.save(check));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "修改指标定义")
    @ApiOperation(value = "修改指标定义", notes = "指标定义信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "crossvalidation", value = "指标定义信息", required = true, dataType = "CvChkInd")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody CvChkInd check) {
        check.setUpdateUser(UserUtil.getLoginName(request));
        check.setUpdateTime(DateUtil.format(new Date()));
        return new ApiResult<>(service.updates(check));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "删除指标定义")
    @ApiOperation(value = "删除指标定义", notes = "删除指标定义信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "指标定义id", required = true, dataType = "string")
    @DeleteMapping("/{indNo}")
    public ApiResult<Boolean> delete(@PathVariable("indNo") String indNo) {
        return new ApiResult<>(service.removeByIndNo(indNo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "查询指标定义")
    @ApiOperation(value = "查询指标定义", notes = "查询指标定义信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "指标定义id", required = true, dataType = "string")
    @GetMapping("/getCvChkInd/{indNo}")
    public ApiResult<CvChkInd> getCvChkInd(@PathVariable("indNo") String indNo) {
        return new ApiResult<>(service.getCvChkInd(indNo));
    }


    @ApiOperation(value = "根据指标编号查询指标定义信息", notes = "根据指标编号查询指标定义信息", httpMethod = "GET")
    @GetMapping("/fetchByIndNo/{indNo}")
    public ApiResult<CvChkIndQuery> fetchByIndNo(@PathVariable("indNo") String indNo) {
        return new ApiResult<>(service.fetchByIndNo(indNo));
    }

    /**
     * 获取所有的指标树
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "获取所有的指标树", notes = "获取所有的指标树", httpMethod = "GET")
    public ApiResult<List<CvChkIndTree>> getCvChkIndTree() {
        return new ApiResult<>(service.getCvChkIndTree());
    }

    /**
     * 获取所有的子指标树
     *
     * @return
     */
    @GetMapping("/treeNode")
    @ApiOperation(value = "获取所有的子指标树", notes = "获取所有的子指标树", httpMethod = "GET")
    public ApiResult<List<TreeNode>> getAllCvChkIndTree() {
        return new ApiResult<>(service.getAllCvChkIndTree());
    }

    /**
     * 获取搜索的子指标树
     *
     * @return
     */
    @GetMapping("/treeSearch/{searchValue}")
    @ApiOperation(value = "获取所有的子指标树", notes = "获取所有的子指标树", httpMethod = "GET")
    public ApiResult<List<TreeNode>> treeSearch(@PathVariable("searchValue") String searchValue) {
        return new ApiResult<>(service.treeSearch(searchValue));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "指标定义导入模板下载")
    @ApiOperation(value = "指标定义导入模板下载", notes = "指标定义导入模板下载", httpMethod = "POST")
    @PostMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("excelModel/cvIndExportTemplate.xlsx");
            InputStream input = classPathResource.getInputStream();
            byte[] data = IOUtils.toByteArray(input);
            response.reset();
            response.setHeader("Content-Disposition", "attachment;");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
            IOUtils.closeQuietly(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "批量导入指标定义信息")
    @ApiOperation(value = "批量导入指标定义信息", notes = "批量导入指标定义信息", httpMethod = "POST")
    @PostMapping("/upload")
    public ApiResult<String> uploadBranchs(@RequestParam("file") MultipartFile file) {
        InputStream inputStream;
        String importSize = null;
        try {
            inputStream = file.getInputStream();
            importSize = service.importInd(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return new ApiResult<>(importSize);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "导出指标定义数据")
    @ApiOperation(value = "导出指标定义数据", notes = "导出指标定义数据", httpMethod = "POST")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("excelModel/cvIndExportTemplate.xlsx");
        InputStream input = classPathResource.getInputStream();
        String tmpDir = System.getProperty("java.io.tmpdir");
        File templateFile = new File(tmpDir + "/cvIndExportTemplate.xlsx");
        if(!templateFile.exists()) {
            FileUtils.copyInputStreamToFile(input, templateFile);
        }
        String tempOutputFileName = "指标排序.xlsx";
        service.exportInd(templateFile.getAbsolutePath(), tempOutputFileName);
        input.close();
        input = new FileInputStream(tempOutputFileName);
        byte[] data = IOUtils.toByteArray(input);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"cvIndExportTemplate.xlsx\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
    }
}
