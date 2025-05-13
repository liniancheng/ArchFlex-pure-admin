package com.adtec.rdc.base.message.log.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NoticeMailSendLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 日志id
     */
    @TableId(value = "LOG_ID", type = IdType.UUID)
    private String logId;

    /**
     * 用户登录名
     */
    @TableField(value = "LOGIN_NAME") 
    private String loginName;
    /**
     * 模板id
     */
    @TableField(value = "TEMP_ID") 
    private String tempId;
    /**
     * 服务器id
     */
    @TableField(value = "SRV_ID") 
    private String srvId;
    /**
     * 发送状态
     */
    @TableField(value = "LOG_STATE") 
    private String logState;
    /**
     * 发送时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;
    /**
     * 异常信息
     */
    @TableField(value = "ERROR_STR") 
    private String errorStr;
}
