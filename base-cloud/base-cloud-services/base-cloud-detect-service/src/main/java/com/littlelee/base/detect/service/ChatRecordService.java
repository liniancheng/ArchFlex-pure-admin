package com.littlelee.base.detect.service;


import com.littlelee.base.detect.model.po.ChatRecord;
import com.littlelee.base.detect.util.ChatTypeEnum;

import java.util.List;

public interface ChatRecordService {

    /**
     * @Description: 保存用户和AI的聊天记录
     * @Author littlelee
     * @param userName
     * @param message
     * @param chatType
     */
    public void saveChatRecord(String userName, String message, ChatTypeEnum chatType);

    /**
     * @Description: 查询用户和AI的历史聊天记录
     * @Author littlelee
     * @param userName
     * @return List<ChatRecord>
     */
    public List<ChatRecord> getChatRecordList(String userName);

}
