package com.littlelee.base.message.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.message.log.mapper.NoticeSmsSendLogMapper;
import com.littlelee.base.message.log.model.po.NoticeSmsSendLog;
import com.littlelee.base.message.log.service.NoticeSmsSendLogService;

@Service
public class NoticeSmsSendLogServiceImpl extends BaseServiceImpl<NoticeSmsSendLogMapper, NoticeSmsSendLog> implements NoticeSmsSendLogService {
	@Autowired
    private NoticeSmsSendLogMapper mapper;
}
