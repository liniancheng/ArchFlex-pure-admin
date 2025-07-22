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
 * 系统消息表 
 * </p>
 *
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
@Data
@Accessors(chain = true)
public class NoticeSystemMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "MSG_ID", type = IdType.ASSIGN_UUID)
    private String msgId;

    /**
     * 消息接收者
     */
    @TableField(value = "RECEIVE_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String receiveUser;
    /**
     * 消息发送者
     */
    @TableField(value = "SEND_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sendUser;
    /**
     * 消息发送时间
     */
    @TableField(value = "SEND_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sendTime;
    /**
     * 消息是否已读状态
     */
    @TableField(value = "READ_FLAG", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String readFlag;
    /**
     * 消息内容
     */
    @TableField(value = "MSG_CONTENT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String msgContent;
    /**
     * 消息主题
     */
    @TableField(value = "MSG_THEME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String msgTheme;

}