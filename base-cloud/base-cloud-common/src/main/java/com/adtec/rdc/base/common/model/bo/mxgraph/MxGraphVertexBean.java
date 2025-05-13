package com.adtec.rdc.base.common.model.bo.mxgraph;

import lombok.Data;

/**
 * 形状
 * @author JTao
 *
 */
@Data
public class MxGraphVertexBean {
	private String vertexId;
	private String vertexName;
	private String vertexStatus;//状态不要求固定值, 不同图形在前端自行判断处理
	private int inputNum;//输入线数量
	private int outputNum;//输出线数量
}

