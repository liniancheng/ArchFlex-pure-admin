package com.adtec.rdc.base.user.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.user.mapper.SysHelpFileMapper;
import com.adtec.rdc.base.user.model.po.SysHelpFileInfo;
import com.adtec.rdc.base.user.model.query.SysHelpFileQuery;
import com.adtec.rdc.base.user.service.SysHelpFileService;
import com.adtec.rdc.base.user.util.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysHelpFileServiceImpl extends ServiceImpl<SysHelpFileMapper, SysHelpFileInfo> implements SysHelpFileService {

	@Autowired 
	private SysHelpFileMapper helFileMapper;
	/**
	 * 将帮助文档的的信息转换成List
	 * @param path
	 * @return
	 */
	@Override
	public SysHelpFileQuery pageByQuery(SysHelpFileQuery query) {
		helFileMapper.pageByQuery(query);
		return query;
	}

	@Override
	public String upload(MultipartFile file) throws IOException {
		if (file.getInputStream() == null
				|| file.getOriginalFilename() == null
				|| "".equals(file.getOriginalFilename())) {
			throw new ServiceException(ErrorCodeEnum.HELP_NULL.getErrorCode(), ErrorCodeEnum.HELP_NULL.getMessage());
		}
		if(file.getSize() > 102400000)
			throw new ServiceException(ErrorCodeEnum.HELP_CLIENT.getErrorCode(), "文件过大，请选择小于100M 的文件！");
		List<SysHelpFileInfo> fileList = helFileMapper.selectList(new QueryWrapper<>());
		fileList.forEach( f ->{
			if(f.getFileName().equals(file.getOriginalFilename())) {
				throw new ServiceException(ErrorCodeEnum.HELP_RE_NAME.getErrorCode(), "已存在相同文件名！");
			}
		});
		SysHelpFileInfo helpFile = new SysHelpFileInfo();
		helpFile.setFileId(UUID.generate());
		helpFile.setFileType(FileUtil.getFileType(file.getOriginalFilename()));
		helpFile.setFileSize(file.getSize());
		helpFile.setFileName(file.getOriginalFilename());
		helpFile.setFileContent(file.getBytes());
		helFileMapper.insert(helpFile);
		return "上传成功！";
	}

	@Override
	public boolean delete(String[] fileIds) throws ServiceException {
		List<String> resultList = new ArrayList<>(fileIds.length);
		Collections.addAll(resultList, fileIds);
		int deleteCount = helFileMapper.deleteBatchIds(resultList);
		return deleteCount>0;
	}

}
