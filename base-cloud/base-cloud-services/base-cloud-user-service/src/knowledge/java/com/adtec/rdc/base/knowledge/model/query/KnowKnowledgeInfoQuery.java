package com.adtec.rdc.base.knowledge.model.query;

import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
@Data
public class KnowKnowledgeInfoQuery extends Page<KnowKnowledgeInfo> {

    /**
     * 知识ID
     */
    private String knowledgeId;
    /**
     * 知识标题
     */
    private String knowledgeTitle;
    /**
     * 知识描述
     */
    private String knowledgeRmk;
    /**
     * 关键词
     */
    private String keyWords;
    /**
     * 知识状态
     */
    private String knowledgeStatus;
    /**
     * 创建用户
     */
    private String createUser;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改用户
     */
    private String modifyUser;
    /**
     * 修改时间
     */
    private String modifyTime;
    /**
     * 知识内容
     */
    private String knowledgeContent;
    /**
     * 分类ID
     */
    private String dirId;
    /**
     * 租户ID
     */
    private String appId;
    /**
           * 知识分享搜索用
     */
    private String knowledgeContentEditType;

}
