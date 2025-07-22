package com.littlelee.base.knowledge.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 知识库附件信息表
 * </p>
 *
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
@Data
@Accessors(chain = true)
public class KnowKnowledgeAttachInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件id
     */
    @TableId(value = "ATT_ID", type = IdType.ASSIGN_UUID)
    private String attId;

    /**
     * 知识库id
     */
    @TableField(value = "KNOWLEDGE_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String knowledgeId;
    /**
     * 租户id
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String appId;
    /**
     * 附件名称
     */
    @TableField(value = "ATT_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String attName;
    /**
     * 附件内容
     */
    /**
	 * 附件内容
	 */
	@TableField(value = "att_content", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private byte[] attContent;

}