package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 类名：SysMessageInfo</br>
 * 模块：</br>
 * 说明：消息实体类</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright ADTEC
 * @since 2020/1/13
 */
@Data
@Accessors(chain = true)
public class SysMessageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(value = "message_id", type = IdType.UUID)
    private String messageId;

    /**
     * 消息类型:1-提醒;2-通知
     */
    @TableField(value = "message_type", strategy = FieldStrategy.IGNORED)
    private String messageType;

    /**
     * 消息状态:0-未读;1-已读
     */
    @TableField(value = "message_status", strategy = FieldStrategy.IGNORED)
    private String messageStatus;

    /**
     * 消息标题
     */
    @TableField(value = "message_title", strategy = FieldStrategy.IGNORED)
    private String messageTitle;

    /**
     * 消息内容
     */
    @TableField(value = "message_content", strategy = FieldStrategy.IGNORED)
    private String messageContent;

    /**
     * 消息接收用户
     */
    @TableField(value = "message_rev_user", strategy = FieldStrategy.IGNORED)
    private String messageRevUser;

    /**
     * 删除标记
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED)
    private String delFlag;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;
    
    /**
     * 应用ID
     */
    @TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
    private String appId;
}
