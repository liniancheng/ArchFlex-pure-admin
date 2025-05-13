package com.adtec.rdc.base.knowledge.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeAttachInfoQuery;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
public interface KnowKnowledgeAttachInfoService extends BaseService<KnowKnowledgeAttachInfo> {
	
	KnowKnowledgeAttachInfoQuery pageByQuery(KnowKnowledgeAttachInfoQuery query);
	
	/**
	 * 	上传知识库附件:<br>
	 * 
	 * @param annoAttach
	 * @return
	 */
	boolean onlySave(String knowledgeId, MultipartFile[] file,String appId);
	
	
	/**
	 * 按知识库id删除
	 * @param annoId
	 * @return
	 */
	Integer removeByKnowledgeId(String knowledgeId);
	/**
	 * 按知识库id查询附件列表
	 * @param annoId
	 * @return
	 */
	List<KnowKnowledgeAttachInfo> getByKnowledgeId(String knowledgeId);

	
	
	
	
}
