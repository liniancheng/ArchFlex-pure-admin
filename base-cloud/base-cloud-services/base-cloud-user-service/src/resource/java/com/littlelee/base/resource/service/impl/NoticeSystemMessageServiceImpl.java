package com.littlelee.base.resource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.resource.mapper.NoticeSystemMessageMapper;
import com.littlelee.base.resource.model.po.NoticeSystemMessage;
import com.littlelee.base.resource.model.query.NoticeSystemMessageQuery;
import com.littlelee.base.resource.service.NoticeSystemMessageService;

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
//		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
        return query;
	}
}
