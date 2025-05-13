package com.adtec.rdc.base.knowledge.model.bo;

import java.time.LocalDateTime;
import java.util.List;

import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;

import lombok.Data;

@Data
public class KnowKnowledgeVo {
    private String knowledgeId;
    private String knowledgeTitle;
    private String knowledgeRmk;
    private String keyWords;
    private String knowledgeStatus;
    private String createUser;
    private LocalDateTime createTime;
    private String modifyUser;
    private LocalDateTime modifyTime;
    private String knowledgeContent;
    private String dirId;
    private String userName;
    private List<KnowKnowledgeAttachInfo> attachs;
}
