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
 * 邮件日志信息表
 * </p>
 *
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
@Data
@Accessors(chain = true)
public class NoticeMailSendLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId(value = "LOG_ID", type = IdType.ASSIGN_UUID)
    private String logId;

    /**
     * 用户登录名
     */
    @TableField(value = "LOGIN_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String loginName;
    /**
     * 服务器id
     */
    @TableField(value = "SRV_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String srvId;
    /**
     * 发送状态
     */
    @TableField(value = "LOG_STATE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String logState;
    /**
     * 发送时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;
    /**
     * 模板id
     */
    @TableField(value = "TEMP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String tempId;
    /**
     * 异常信息
     */
    @TableField(value = "ERROR_STR", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String errorStr;

    @TableField(exist = false)
    private String srvName;

    @TableField(exist = false)
    private String tempName;

}