package com.littlelee.base.resource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.resource.mapper.NoticeSmsSendLogMapper;
import com.littlelee.base.resource.model.po.NoticeSmsSendLog;
import com.littlelee.base.resource.model.query.NoticeSmsSendLogQuery;
import com.littlelee.base.resource.service.NoticeSmsSendLogService;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
@Service
public class NoticeSmsSendLogServiceImpl extends BaseServiceImpl<NoticeSmsSendLogMapper, NoticeSmsSendLog> implements NoticeSmsSendLogService {
	@Autowired
    private NoticeSmsSendLogMapper mapper;
	
	@Override
	public NoticeSmsSendLogQuery pageByQuery(NoticeSmsSendLogQuery query) {
		mapper.pageByQuery(query);
        return query;
	}
}
