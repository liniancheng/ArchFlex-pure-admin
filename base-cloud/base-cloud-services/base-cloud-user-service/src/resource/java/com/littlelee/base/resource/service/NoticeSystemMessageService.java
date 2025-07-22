package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeSystemMessage;
import com.littlelee.base.resource.model.query.NoticeSystemMessageQuery;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
public interface NoticeSystemMessageService extends BaseService<NoticeSystemMessage> {
	NoticeSystemMessageQuery pageByQuery(NoticeSystemMessageQuery query);
}
