package com.littlelee.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户评论信息表
 * </p>
 *
 * @author littlelee
 * @date 2020-07-28 15:38:50
 */
@Data
@Accessors(chain = true)
public class SysUserCommentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "COMMENT_ID", type = IdType.ASSIGN_UUID)
    private String commentId;
    /**
     * 评论内容
     */
    @TableField(value = "COMMENT_CONTENT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String commentContent;
    /**
     * 评论对象ID
     */
    @TableField(value = "COMMENT_OBJID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String commentObjid;
    /**
     * 创建用户
     */
    @TableField(value = "CREATE_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String createUser;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;
    /**
     * 应用id
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String appId;
    
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private boolean myComment;
}