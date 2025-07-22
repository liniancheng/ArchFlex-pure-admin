package com.littlelee.base.layout.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.layout.model.po.SysLayoutItemRel;

/**
 * @author littlelee
 * @date 2020-08-25 15:08:26
 */
public interface SysLayoutItemRelService extends BaseService<SysLayoutItemRel> {
	boolean isExist(SysLayoutItemRel entity);

	boolean saveLists(List<SysLayoutItemRel> listRels, String layId);

	List<SysLayoutItemRel> listRels(String id, String appId);
}
