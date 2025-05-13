package com.adtec.rdc.base.common.easyexcel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EasyExcelListener<T> extends AnalysisEventListener<T> {
	
	private List<T> list = new ArrayList<T>();
	private int index;
	
	public EasyExcelListener(int index) {
		super();
		this.index = index;
	}

	public EasyExcelListener() {
		super();
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		int rowNum = context.getCurrentRowNum();
		if (rowNum>=index) {
			list.add(data);
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.debug("数据解析完毕！");
	}

	/**
	 * 数据转换异常时，停止读取
	 */
	@Override
	public void onException(Exception exception, AnalysisContext context) throws Exception {
		log.error("数据解析异常");
		int col = 0, row = 0;
		if(exception instanceof ExcelDataConvertException) {
			ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            col = excelDataConvertException.getColumnIndex();
            row = excelDataConvertException.getRowIndex();
            log.error("第{}行，第{}列解析异常，数据为:{}", row, col , excelDataConvertException.getCellData());
		}
		//需要抛出RuntimeException
		throw new ExcelAnalysisException(String.format("出错行：%s,出错列：%s", row,col));
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
