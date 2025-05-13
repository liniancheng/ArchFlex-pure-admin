package com.adtec.rdc.base.message.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.message.log.mapper.NoticeMailSendLogMapper;
import com.adtec.rdc.base.message.log.model.po.NoticeMailSendLog;
import com.adtec.rdc.base.message.log.service.NoticeMailSendLogService;

@Service
public class NoticeMailSendLogServiceImpl extends BaseServiceImpl<NoticeMailSendLogMapper, NoticeMailSendLog> implements NoticeMailSendLogService {
	@Autowired
    private NoticeMailSendLogMapper mapper;
}
