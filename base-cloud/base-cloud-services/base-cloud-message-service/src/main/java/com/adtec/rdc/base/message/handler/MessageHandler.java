package com.adtec.rdc.base.message.handler;

import com.adtec.rdc.base.common.template.MessageTemplate;

/**
 * @author: JTao
 * @date: 2018/11/30 17:10
 */
public interface MessageHandler {

    /**
     * 参数校验
     * @param messageTemplate
     */
    void check(MessageTemplate messageTemplate);

    /**
     * 执行发送短信
     * @param messageTemplate
     * @return
     */
    Boolean execute(MessageTemplate messageTemplate);

    /**
     * 发送失败回调处理
     * @param messageTemplate
     */
    void fail(MessageTemplate messageTemplate);

    /**
     * 发送短信主入口
     * @param messageTemplate
     */
    void handleMessage(MessageTemplate messageTemplate);

}
