package com.adtec.rdc.base.user.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.user.model.query.SysOperlogTextQuery;
import com.adtec.rdc.base.user.service.SysFileLogInfoService;
import com.adtec.rdc.base.user.service.impl.SysFileLogInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/logfile")
@Api(value = "日志文件controller", tags = {"日志文件操作接口"})
public class SysFileLogInfoController {
	@Autowired
    private SysFileLogInfoService sysLogService;
	
    @ApiOperation(value = "日志下载分页查询", notes = "日志下载分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "sysLogQuery", value = "日志下载查询类", required = false, dataType = "SysOperlogTextQuery")
    @GetMapping("/txtPage")
    public ApiResult<SysOperlogTextQuery> pageTextByQuery(SysOperlogTextQuery sysLogQuery){
    	return new ApiResult<>(sysLogService.pageTextByQuery(sysLogQuery));
    }
    
    @ApiOperation(value = "批量下载操作日志", notes = "下载操作日志信息", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "操作日志ids", required = true, dataType = "list")
    @GetMapping("/downloadTxt/{name}")
    public void downloadTxt(@PathVariable("name") String name,HttpServletResponse response) throws IOException {
    	List<File> files = searchFiles(new File(SysFileLogInfoServiceImpl.basePath), name);
    	File file = files.get(0); 
		response.reset();
        response.addHeader("Content-Length", "" + file.length());
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
        response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(getBytesByFile(file), response.getOutputStream());
    }
    
    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<File> searchFiles(File folder, final String keyword) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile())
            result.add(folder);
 
        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                if (file.getName().toLowerCase().contains(keyword)) {
                    return true;
                }
                return false;
            }
        });
 
        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    // 如果是文件则将文件添加到结果列表中
                    result.add(file);
                } else {
                    // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                    result.addAll(searchFiles(file, keyword));
                }
            }
        }
 
        return result;
    }

}
