package com.adtec.rdc.base.user.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.user.model.po.SysHelpFileInfo;
import com.adtec.rdc.base.user.model.query.SysHelpFileQuery;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysHelpFileService extends IService<SysHelpFileInfo> {

	SysHelpFileQuery pageByQuery(SysHelpFileQuery query);

	String upload(MultipartFile file) throws IllegalStateException, IOException;

	boolean delete(String[] fileIds);
}
