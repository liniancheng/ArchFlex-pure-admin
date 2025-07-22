package com.littlelee.base.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysHelpFileInfo implements Serializable, Comparable<SysHelpFileInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件id
	 */
    @TableId(value = "HELP_FILE_ID", type = IdType.ASSIGN_UUID)
	private String fileId;
	/**
	 * 文件名称
	 */
    @TableField(value = "HELP_FILE_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String fileName;
	/**
	 * 文件大小
	 */
    @TableField(value = "HELP_FILE_SIZE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private long fileSize;
	/**
	 * 类型
	 */
    @TableField(value = "HELP_FILE_TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String fileType;
    /**
     * 文件
     */
    @TableField(value = "HELP_FILE_CONTENT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private byte[] fileContent;
    
	@Override
	public int compareTo(SysHelpFileInfo o) {
		return this.fileName.compareTo(o.fileName);
	}
}
