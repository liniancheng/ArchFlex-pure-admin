package com.adtec.rdc.base.anno.service;

import com.adtec.rdc.base.common.base.service.BaseService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.anno.model.po.SysAnnoAttachInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;

/**
 * @author dengchf
 * @date 2019-12-09 16:56:44
 */
public interface SysAnnoAttachInfoService extends BaseService<SysAnnoAttachInfo> {

	/**
	 * 	上传公告附件:<br>
	 * 
	 * @param annoAttach
	 * @return
	 */
	boolean onlySave(String annoId, MultipartFile[] files,String appId);
	
	/**
	 * 按公告id删除
	 * @param annoId
	 * @return
	 */
	Integer removeByAnnoId(String annoId);
	/**
	 * 按公告id查询附件列表
	 * @param annoId
	 * @return
	 */
	List<SysAnnoAttach> getByAnnoId(String annoId);

}
