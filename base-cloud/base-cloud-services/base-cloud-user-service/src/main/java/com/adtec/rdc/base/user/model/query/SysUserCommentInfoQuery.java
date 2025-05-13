package com.adtec.rdc.base.user.model.query;

import java.util.Date;

import com.adtec.rdc.base.user.model.po.SysUserCommentInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-07-28 15:38:50
 */
@Data
public class SysUserCommentInfoQuery extends Page<SysUserCommentInfo> {

    /**
     * 评论id
     */
    private String commentId;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 评论对象ID
     */
    private String commentObjid;
    /**
     * 创建用户
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当前用户ID
     */
    private String  currentUserId;
}
