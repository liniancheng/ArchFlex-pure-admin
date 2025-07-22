package com.littlelee.base.knowledge.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.littlelee.base.common.base.service.MessageQueueService;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.constants.MqQueueNameConstant;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.UUID;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.knowledge.mapper.KnowKnowledgeAttachInfoMapper;
import com.littlelee.base.knowledge.mapper.KnowKnowledgeInfoMapper;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeInfoQuery;
import com.littlelee.base.knowledge.service.KnowKnowledgeInfoService;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
@Service
@Transactional
public class KnowKnowledgeInfoServiceImpl extends BaseServiceImpl<KnowKnowledgeInfoMapper, KnowKnowledgeInfo>
		implements KnowKnowledgeInfoService {
	@Autowired
	private KnowKnowledgeInfoMapper mapper;
	@Autowired
    private KnowKnowledgeAttachInfoMapper attMapper;
	@Autowired
	private MessageQueueService messageQueueService;

	@Override
	public KnowKnowledgeInfoQuery pageByQuery(KnowKnowledgeInfoQuery query) {
		query.addOrder(OrderItem.desc("CREATE_TIME")).addOrder(OrderItem.desc("MODIFY_TIME"));
		mapper.pageByQuery(query);
		return query;
	}

	@Override
	public boolean removeById(Serializable id) {
		boolean ok = super.removeById(id);
		if (ok) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id.toString());
			map.put("index", "base_kw");
			messageQueueService.convertAndSend(MqQueueNameConstant.ELASTICSEARCH_QUEUE, map);
		}
		return ok;
	}
	@Override
	public boolean deleteById(String id) {
		super.removeById(id);
		Map<String,Object> columnMap = new HashMap<String, Object>();
		columnMap.put("KNOWLEDGE_ID", id);
		attMapper.deleteByMap(columnMap);
		return Boolean.TRUE;
	}
	@Override
	public String onlySave(KnowKnowledgeInfo entity) {
		// 判断名称
		if (mapper.isSameTitle(entity.getKnowledgeTitle(),entity.getDirId(),entity.getAppId()) > 0) {
			throw new ServiceException(ErrorCodeEnum.KN_RE_NAME.getErrorCode(),
					ErrorCodeEnum.KN_RE_NAME.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		entity.setKnowledgeId(UUID.generate());
		super.save(entity);
		return entity.getKnowledgeId();
	}
	@Override
	public String onlyUpdate(KnowKnowledgeInfo entity) {
		boolean existKnowTitle = mapper.isExistKnowTitle(entity);
		// 判断名称
		if (existKnowTitle) {
			throw new ServiceException(ErrorCodeEnum.KN_RE_NAME.getErrorCode(),
					ErrorCodeEnum.KN_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		super.updateById(entity);
		return entity.getKnowledgeId();
	}

	@Override
	public KnowKnowledgeInfo fineById(String id) {
		KnowKnowledgeInfo know = mapper.selectById(id);
		if(know == null) {
			return null;
		}
		List<KnowKnowledgeAttachInfo> attachList = attMapper.getByKnowId(id);
		know.setFileList(attachList);
		return know;
	}
}
