package com.adtec.rdc.base.resource.model.query;

import com.adtec.rdc.base.resource.model.po.NoticeSmsSendLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
@Data
public class NoticeSmsSendLogQuery extends Page<NoticeSmsSendLog> {

    /**
     * 主键
     */
    private String logId;
    /**
     * 服务器ID
     */
    private String srvId;
    /**
     * 发送时间
     */
    private String smsSendTime;
    /**
     * 发送用户
     */
    private String smsSendUser;
    /**
     * 所有接收者
     */
    private String smsReceiveUser;
    /**
     * 发送内容
     */
    private String sendContent;
    /**
     * 发送状态
     */
    private String sendStatus;

    private String appId;
}
