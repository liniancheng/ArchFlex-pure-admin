package com.littlelee.base.resource.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.resource.mapper.NoticeMailSendLogMapper;
import com.littlelee.base.resource.model.po.NoticeMailSendLog;
import com.littlelee.base.resource.model.query.NoticeMailSendLogQuery;
import com.littlelee.base.resource.service.NoticeMailSendLogService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
@Service
public class NoticeMailSendLogServiceImpl extends BaseServiceImpl<NoticeMailSendLogMapper, NoticeMailSendLog> implements NoticeMailSendLogService {
	@Autowired
    private NoticeMailSendLogMapper mapper;
	
	@Override
	public NoticeMailSendLogQuery pageByQuery(NoticeMailSendLogQuery query) {
		query.addOrder(OrderItem.desc("create_time"));
		mapper.pageByQuery(query);
        return query;
	}
}
