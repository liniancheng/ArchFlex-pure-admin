package com.littlelee.base.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 短信服务器发送日志表 
 * </p>
 *
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
@Data
@Accessors(chain = true)
public class NoticeSmsSendLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "LOG_ID", type = IdType.ASSIGN_UUID)
    private String logId;

    /**
     * 服务器ID
     */
    @TableField(value = "SRV_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String srvId;
    /**
     * 发送时间
     */
    @TableField(value = "SMS_SEND_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String smsSendTime;
    /**
     * 发送用户
     */
    @TableField(value = "SMS_SEND_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String smsSendUser;
    /**
     * 所有接收者
     */
    @TableField(value = "SMS_RECEIVE_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String smsReceiveUser;
    /**
     * 发送内容
     */
    @TableField(value = "SEND_CONTENT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sendContent;
    /**
     * 发送状态
     */
    @TableField(value = "SEND_STATUS", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sendStatus;

}