package com.adtec.rdc.base.user.model.po;

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
    @TableId(value = "HELP_FILE_ID", type = IdType.UUID)
	private String fileId;
	/**
	 * 文件名称
	 */
    @TableField(value = "HELP_FILE_NAME", strategy = FieldStrategy.IGNORED)
	private String fileName;
	/**
	 * 文件大小
	 */
    @TableField(value = "HELP_FILE_SIZE", strategy = FieldStrategy.IGNORED)
	private long fileSize;
	/**
	 * 类型
	 */
    @TableField(value = "HELP_FILE_TYPE", strategy = FieldStrategy.IGNORED)
	private String fileType;
    /**
     * 文件
     */
    @TableField(value = "HELP_FILE_CONTENT", strategy = FieldStrategy.IGNORED)
	private byte[] fileContent;
    
	@Override
	public int compareTo(SysHelpFileInfo o) {
		return this.fileName.compareTo(o.fileName);
	}
}
