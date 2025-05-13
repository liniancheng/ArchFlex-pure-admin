package com.adtec.rdc.base.knowledge.model.query;

import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
@Data
public class KnowKnowledgeAttachInfoQuery extends Page<KnowKnowledgeAttachInfo> {

    /**
     * 附件id
     */
    private String attId;
    /**
     * 知识库id
     */
    private String knowledgeId;
    /**
     * 租户id
     */
    private String appId;
    /**
     * 附件名称
     */
    private String attName;
    /**
     * 附件内容
     */
    private byte[] attContent;

}
