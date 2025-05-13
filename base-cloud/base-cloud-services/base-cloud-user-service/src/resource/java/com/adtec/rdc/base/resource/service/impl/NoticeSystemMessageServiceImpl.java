package com.adtec.rdc.base.resource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.resource.mapper.NoticeSystemMessageMapper;
import com.adtec.rdc.base.resource.model.po.NoticeSystemMessage;
import com.adtec.rdc.base.resource.model.query.NoticeSystemMessageQuery;
import com.adtec.rdc.base.resource.service.NoticeSystemMessageService;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
@Service
public class NoticeSystemMessageServiceImpl extends BaseServiceImpl<NoticeSystemMessageMapper, NoticeSystemMessage> implements NoticeSystemMessageService {
	@Autowired
    private NoticeSystemMessageMapper mapper;
	
	@Override
	public NoticeSystemMessageQuery pageByQuery(NoticeSystemMessageQuery query) {
//		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
}
