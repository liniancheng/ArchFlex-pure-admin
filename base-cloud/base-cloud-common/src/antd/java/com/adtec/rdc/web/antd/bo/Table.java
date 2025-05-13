package com.adtec.rdc.web.antd.bo;

import java.util.LinkedHashMap;
import java.util.List;

import lombok.Data;

/**
 * 动态表格
 * @author JTao
 *
 */
@Data
public class Table {
	/**
     * 宽度
     */
	private Integer width;
	/**
     * 总记录数
     */
	private Long total;
	/**
     * 表头
     */
    private List<TableColumn> columns;
    /**
     * 数据
     */
    private List<LinkedHashMap<String, Object>> datas;
}
