package com.littlelee.base.anno.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.exception.ServiceException;

import java.util.List;

import com.littlelee.base.anno.model.po.SysAnnoInfo;
import com.littlelee.base.anno.model.query.SysAnnoAttach;
import com.littlelee.base.anno.model.query.SysAnnoInfoQuery;

/**
 * @author littlelee
 * @date 2019-12-01 20:25:26
 */
public interface SysAnnoInfoService extends BaseService<SysAnnoInfo> {
	SysAnnoInfoQuery pageByQuery(SysAnnoInfoQuery query);
	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	SysAnnoInfo fineById(String id);
	/**
	 * 保存
	 * @param anno
	 * @return
	 * @throws ServiceException
	 */
	String onlySave(SysAnnoInfo anno) throws ServiceException;
	/**
	 * 更新
	 * @param anno
	 * @return
	 * @throws ServiceException
	 */
	String onlyUpdate(SysAnnoInfo anno)throws ServiceException;
	/**
	 * 判断标题是否重复
	 * @param anno
	 * @return
	 */
	boolean isExistAnnoTitle(SysAnnoInfo anno);
	/**
	 * 按id删除公告（含附件）
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);
	
	/**
	 * 租户公告
	 * @param query
	 * @return
	 */
	SysAnnoInfoQuery appPageByQuery(SysAnnoInfoQuery query);
	
	/**
	 * 获取附件
	 * @param attId
	 * @return
	 */
	List<SysAnnoAttach> getAttachById(String annoId);
}
