package com.littlelee.base.common.model.bo.mxgraph;

import lombok.Data;

/**
 * 连线
 * @author littlelee
 *
 */
@Data
public class MxGraphEdgeBean {
	private String title;
	private String sourceVertexId;
	private String targetVertexId;
}
