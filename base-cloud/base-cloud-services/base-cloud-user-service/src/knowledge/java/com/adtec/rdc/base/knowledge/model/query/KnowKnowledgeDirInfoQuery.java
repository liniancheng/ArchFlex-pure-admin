package com.adtec.rdc.base.knowledge.model.query;

import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@Data
public class KnowKnowledgeDirInfoQuery extends Page<KnowKnowledgeDirInfo> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 分类ID
     */
    private String dirId;
    /**
     * 分类名称
     */
    private String dirName;
    /**
     * 父ID
     */
    private String parentId;
    /**
     * 分类描述
     */
    private String dirRmk;
    /**
     * 排序
     */
    private Integer dirOrder;
    /**
     * 租户ID
     */
    private String appId;

}
