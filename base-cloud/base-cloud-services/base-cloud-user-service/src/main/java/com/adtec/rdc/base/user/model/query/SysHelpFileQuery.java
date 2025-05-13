package com.adtec.rdc.base.user.model.query;

import com.adtec.rdc.base.user.model.po.SysHelpFileInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author liushp
 */
@Data
public class SysHelpFileQuery extends Page<SysHelpFileInfo> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件id
	 */
	private String fileId;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件样式
	 */
	private String fileIcon;
	/**
	 * 文件样式
	 */
	private long fileSize;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 
	 */
	private String[] fileIds;

	private String appId;
}
