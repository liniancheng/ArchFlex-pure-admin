package com.adtec.rdc.base.knowledge.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.knowledge.mapper.KnowKnowledgeAttachInfoMapper;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeAttachInfoQuery;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeAttachInfoService;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
@Service
public class KnowKnowledgeAttachInfoServiceImpl extends BaseServiceImpl<KnowKnowledgeAttachInfoMapper, KnowKnowledgeAttachInfo> implements KnowKnowledgeAttachInfoService {
	@Autowired
    private KnowKnowledgeAttachInfoMapper mapper;
	
	@Override
	public KnowKnowledgeAttachInfoQuery pageByQuery(KnowKnowledgeAttachInfoQuery query) {
		mapper.pageByQuery(query);
        return query;
	}
	@Override
	public Integer removeByKnowledgeId(String knowledgeId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("knowledgeId", knowledgeId);
		return mapper.deleteByMap(map);
	}
	
	@Override
	public List<KnowKnowledgeAttachInfo> getByKnowledgeId(String knowledgeId){
		return mapper.getByKnowledgeId(knowledgeId);
	}

	@Override
	public boolean onlySave(String knowledgeId, MultipartFile[] files, String appId) {
		if(files == null ||  files.length ==0 ) {
			throw new ServiceException("附件为空，请检查！");
		}
		for (MultipartFile file : files) {
			if(StringUtils.isEmpty(knowledgeId)) {
					throw new ServiceException("知识库id为空，请检查！");
			}
			KnowKnowledgeAttachInfo attach = new KnowKnowledgeAttachInfo();
			attach.setKnowledgeId(knowledgeId);
			String tempFileName = file.getOriginalFilename();
			if(!StringUtils.isEmpty(tempFileName)) {
				tempFileName = tempFileName.replaceAll("\\\\", "/");
				if(tempFileName.indexOf("/") > 0) {
					tempFileName = tempFileName.substring(tempFileName.lastIndexOf("/")+1,tempFileName.length());
				}
			}
			attach.setAttName(tempFileName);
			if(mapper.isExistName(attach.getAttName())) {
				throw new ServiceException("附件名已经存在，请重新命名后再上传！");
			}
			try {
				attach.setAttContent(file.getBytes());
				attach.setAppId(appId);
				super.save(attach);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ServiceException("附件保存异常，请检查！");
			}
		}
		return true;
	}
}
