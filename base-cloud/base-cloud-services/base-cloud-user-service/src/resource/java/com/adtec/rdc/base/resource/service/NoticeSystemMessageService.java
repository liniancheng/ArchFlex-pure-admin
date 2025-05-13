package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeSystemMessage;
import com.adtec.rdc.base.resource.model.query.NoticeSystemMessageQuery;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
public interface NoticeSystemMessageService extends BaseService<NoticeSystemMessage> {
	NoticeSystemMessageQuery pageByQuery(NoticeSystemMessageQuery query);
}
