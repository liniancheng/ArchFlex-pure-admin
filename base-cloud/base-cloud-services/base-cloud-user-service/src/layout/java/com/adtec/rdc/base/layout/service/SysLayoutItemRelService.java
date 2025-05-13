package com.adtec.rdc.base.layout.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;

/**
 * @author dengchf
 * @date 2020-08-25 15:08:26
 */
public interface SysLayoutItemRelService extends BaseService<SysLayoutItemRel> {
	boolean isExist(SysLayoutItemRel entity);

	boolean saveLists(List<SysLayoutItemRel> listRels, String layId);

	List<SysLayoutItemRel> listRels(String id, String appId);
}
