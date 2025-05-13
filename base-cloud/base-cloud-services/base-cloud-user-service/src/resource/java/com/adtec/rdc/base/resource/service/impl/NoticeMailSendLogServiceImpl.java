package com.adtec.rdc.base.resource.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.resource.mapper.NoticeMailSendLogMapper;
import com.adtec.rdc.base.resource.model.po.NoticeMailSendLog;
import com.adtec.rdc.base.resource.model.query.NoticeMailSendLogQuery;
import com.adtec.rdc.base.resource.service.NoticeMailSendLogService;

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
		query.setDesc("create_time");
		mapper.pageByQuery(query);
        return query;
	}
}
