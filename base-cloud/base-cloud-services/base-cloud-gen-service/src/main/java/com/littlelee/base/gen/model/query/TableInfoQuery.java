package com.littlelee.base.gen.model.query;

import com.littlelee.base.gen.model.po.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author: littlelee
 * @date: 2018/11/8 10:01
 */
@Data
public class TableInfoQuery extends Page<TableInfo> {

	private static final long serialVersionUID = 1L;
	
	private String tableName;

}
