package com.adtec.rdc.base.common.easyexcel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * @author: dengchf
 * @date: 2020年5月8日
 * @Description: easyexcel 自定义拦截器，添加批注
 * @version V1.0
 * @Copyright: adtec
 */
public class EasyExcelCommentWriterHandler extends AbstractRowWriteHandler {
	/**
	 * 批注信息（整行的批注，没有批注的列也要给个空值）
	 */
	private List<String> listComment = new ArrayList<String>();

	public EasyExcelCommentWriterHandler(List<String> listComment) {
		this.listComment = listComment;
	}

	@Override
	public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
			Integer relativeRowIndex, Boolean isHead) {
		if (isHead) {
			Sheet sheet = writeSheetHolder.getSheet();
			Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
			if (listComment != null && listComment.size() > 0) {
				for (int index = 0, len = listComment.size(); index < len; index++) {
					String value = listComment.get(index);
					if (StringUtils.isEmpty(value)) {
						continue;
					}
					Comment comment = drawingPatriarch.createCellComment(
							new XSSFClientAnchor(0, 0, 0, 0, (short) index, 0, (short) 2 * index, 1 + index));
					comment.setString(new XSSFRichTextString(value));
					sheet.getRow(0).getCell(index).setCellComment(comment);

				}
			}
		}

	}

}
