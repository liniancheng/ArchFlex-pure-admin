package com.littlelee.base.knowledge.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 知识库表
 * </p>
 *
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
@Data
@Accessors(chain = true)
public class KnowKnowledgeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 知识ID
     */
    @TableId(value = "KNOWLEDGE_ID", type = IdType.ASSIGN_UUID)
    private String knowledgeId;

    /**
     * 知识标题
     */
    @TableField(value = "KNOWLEDGE_TITLE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeTitle;
    /**
     * 知识描述
     */
    @TableField(value = "KNOWLEDGE_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeRmk;
    /**
     * 关键词
     */
    @TableField(value = "KEY_WORDS", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String keyWords;
    /**
     * 知识状态
     */
    @TableField(value = "KNOWLEDGE_STATUS", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeStatus;
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
     * 修改用户
     */
    @TableField(value = "MODIFY_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String modifyUser;
    /**
     * 修改时间
     */
    @TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private LocalDateTime modifyTime;
    /**
     * 知识内容
     */
    @TableField(value = "KNOWLEDGE_CONTENT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeContent;
    /**
     * 分类ID
     */
    @TableField(value = "DIR_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String dirId;
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String appId;
    /**
     * 知识内容类型
     */
    @TableField(value = "KNOWLEDGE_CONTENT_EDIT_TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeContentEditType;
    
    @TableField(exist = false)
	private List<KnowKnowledgeAttachInfo> fileList;

}