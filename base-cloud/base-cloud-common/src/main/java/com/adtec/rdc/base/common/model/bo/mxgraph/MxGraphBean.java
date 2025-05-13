package com.adtec.rdc.base.common.model.bo.mxgraph;

import java.util.List;

import lombok.Data;

/**
 * mxgraph图形
 * @author JTao
 *
 */
@Data
public class MxGraphBean {
	private List<MxGraphVertexBean> vertexs;
	private List<MxGraphEdgeBean> edges;
}
