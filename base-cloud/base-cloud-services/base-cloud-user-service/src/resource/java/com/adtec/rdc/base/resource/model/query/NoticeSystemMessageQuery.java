package com.adtec.rdc.base.resource.model.query;

import com.adtec.rdc.base.resource.model.po.NoticeSystemMessage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
@Data
public class NoticeSystemMessageQuery extends Page<NoticeSystemMessage> {

    /**
     * 主键
     */
    private String msgId;
    /**
     * 消息接收者
     */
    private String receiveUser;
    /**
     * 消息发送者
     */
    private String sendUser;
    /**
     * 消息发送时间
     */
    private String sendTime;
    /**
     * 消息是否已读状态
     */
    private String readFlag;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 消息主题
     */
    private String msgTheme;

}
