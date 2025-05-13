package com.adtec.rdc.base.anno.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.anno.mapper.SysAnnoAttachInfoMapper;
import com.adtec.rdc.base.anno.model.po.SysAnnoAttachInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;
import com.adtec.rdc.base.anno.service.SysAnnoAttachInfoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dengchf
 * @date 2019-12-09 16:56:44
 */
@Service
public class SysAnnoAttachInfoServiceImpl extends BaseServiceImpl<SysAnnoAttachInfoMapper, SysAnnoAttachInfo>
		implements SysAnnoAttachInfoService {
	@Autowired
	private SysAnnoAttachInfoMapper mapper;

	@Override
	public boolean onlySave(String annoId, MultipartFile[] files, String appId) {
		boolean flg = true;
		if(files == null ||  files.length ==0 ) {
			flg = false;
			throw new ServiceException(ErrorCodeEnum.ANNO_ATT_NULL.getErrorCode(), ErrorCodeEnum.ANNO_ATT_NULL.getMessage());
		}
		for (MultipartFile file : files) {
			if(StringUtils.isEmpty(annoId)) {
				throw new ServiceException(ErrorCodeEnum.ANNO_NULL_ID.getErrorCode(), ErrorCodeEnum.ANNO_NULL_ID.getMessage());
			}
			SysAnnoAttachInfo attach = new SysAnnoAttachInfo();
			attach.setAnnoId(annoId);
			String tempFileName = file.getOriginalFilename();
			if(!StringUtils.isEmpty(tempFileName)) {
				tempFileName = tempFileName.replaceAll("\\\\", "/");
				if(tempFileName.indexOf("/") > 0) {
					tempFileName = tempFileName.substring(tempFileName.lastIndexOf("/")+1,tempFileName.length());
				}
			}
			
			attach.setAttName(tempFileName);
			if(mapper.isExistName(annoId, attach.getAttName())) {
				throw new ServiceException(ErrorCodeEnum.ANNO_ATT_RE_NAME.getErrorCode(), ErrorCodeEnum.ANNO_ATT_RE_NAME.getMessage());
			}
			try {
				attach.setAttContent(file.getBytes());
			} catch (IOException e) {
				flg = false;
				e.printStackTrace();
				throw new ServiceException(ErrorCodeEnum.ANNO_ATT_SAVE.getErrorCode(), ErrorCodeEnum.ANNO_ATT_SAVE.getMessage());
			}
			attach.setAppId(appId);
			super.save(attach);
		}
		
		return flg;
	}

	@Override
	public Integer removeByAnnoId(String annoId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("annoId", annoId);
		return mapper.deleteByMap(map);
	}
	
	@Override
	public List<SysAnnoAttach> getByAnnoId(String annoId){
		return mapper.getByAnnoId(annoId);
	}

}
