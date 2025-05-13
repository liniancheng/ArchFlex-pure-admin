package com.adtec.rdc.base.message.log.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NoticeSmsSendLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "LOG_ID", type = IdType.UUID)
    private String logId;

    /**
     * 服务器ID
     */
    @TableField(value = "SRV_ID") 
    private String srvId;
    /**
     * 发送时间
     */
    @TableField(value = "SMS_SEND_TIME") 
    private String smsSendTime;
    /**
     * 发送用户
     */
    @TableField(value = "SMS_SEND_USER") 
    private String smsSendUser;
    /**
     * 所有接收者
     */
    @TableField(value = "SMS_RECEIVE_USER") 
    private String smsReceiveUser;
    /**
     * 发送内容
     */
    @TableField(value = "SEND_CONTENT") 
    private String sendContent;
    /**
     * 发送状态
     */
    @TableField(value = "SEND_STATUS") 
    private String sendStatus;
}
