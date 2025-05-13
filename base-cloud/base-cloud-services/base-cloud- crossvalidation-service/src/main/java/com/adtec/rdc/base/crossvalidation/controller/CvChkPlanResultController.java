package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanResultQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanResultService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
@RestController
@RequestMapping("/cvchkresult")
@Api(value = "指标检核计划结果controller", tags = {"指标检核计划结果操作接口"})
public class CvChkPlanResultController {
	private static final String FUNC_NAME = "指标检核计划结果功能";

    @Autowired
    private CvChkPlanResultService service;

    @ApiOperation(value = "指标检核计划结果展示", notes = "指标检核计划结果展示", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "指标检核计划结果信息查询类", required = false, dataType = "CvChkPlanResultQuery")
    @PostMapping("/showResult")
    public ApiResult<CvChkPlanResultQuery> showResult(@RequestBody CvChkPlanResultQuery query){
        return new ApiResult<>(service.showResult(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_CHECK_SERVICE, moduleName = FUNC_NAME, actionName = "导出指标校验结果")
    @ApiOperation(value = "导出指标校验结果", notes = "导出指标校验结果", httpMethod = "POST")
    @PostMapping("/exportData")
    public void exportData(@RequestBody CvChkPlanResultQuery query,HttpServletResponse response) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("excleModel/cvResultExportTemplate.xlsx");
        InputStream input = classPathResource.getInputStream();
        String tmpDir = System.getProperty("java.io.tmpdir");
        File templateFile = new File(tmpDir + "/cvResultExportTemplate.xlsx");
        if(!templateFile.exists()) {
            FileUtils.copyInputStreamToFile(input, templateFile);
        }
        String tempOutputFileName = tmpDir + "/" + UUID.generate() + ".xlsx";
        service.exportResult(query,templateFile.getAbsolutePath(), tempOutputFileName);
        input.close();
        input = new FileInputStream(tempOutputFileName);
        byte[] data = IOUtils.toByteArray(input);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"cvResultExportTemplate.xlsx\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
    }
}
