package com.adtec.rdc.base.message.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.message.log.mapper.NoticeSmsSendLogMapper;
import com.adtec.rdc.base.message.log.model.po.NoticeSmsSendLog;
import com.adtec.rdc.base.message.log.service.NoticeSmsSendLogService;

@Service
public class NoticeSmsSendLogServiceImpl extends BaseServiceImpl<NoticeSmsSendLogMapper, NoticeSmsSendLog> implements NoticeSmsSendLogService {
	@Autowired
    private NoticeSmsSendLogMapper mapper;
}
