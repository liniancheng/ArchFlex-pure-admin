package com.littlelee.base.resource.mapper;

import com.littlelee.base.resource.model.po.NoticeMailSendLog;
import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.resource.model.query.NoticeMailSendLogQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
public interface NoticeMailSendLogMapper extends BaseMapper<NoticeMailSendLog> {
    /**
     * 邮件日志查询分页信息
     * @param query
     * @return
     */
    IPage<NoticeMailSendLog> pageByQuery(NoticeMailSendLogQuery query);
}
