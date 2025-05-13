package com.adtec.rdc.base.common.model.bo.mxgraph;

import lombok.Data;

/**
 * 连线
 * @author JTao
 *
 */
@Data
public class MxGraphEdgeBean {
	private String title;
	private String sourceVertexId;
	private String targetVertexId;
}
