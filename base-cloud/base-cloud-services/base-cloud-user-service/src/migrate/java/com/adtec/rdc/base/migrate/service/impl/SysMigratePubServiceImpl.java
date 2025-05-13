package com.adtec.rdc.base.migrate.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.migrate.config.BaseDBConfig;
import com.adtec.rdc.base.migrate.enums.MigrateConverTypeEnum;
import com.adtec.rdc.base.migrate.mapper.SysMigrateInfoMapper;
import com.adtec.rdc.base.migrate.model.bo.MigrateExport;
import com.adtec.rdc.base.migrate.model.bo.MigrateTable;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;
import com.adtec.rdc.base.migrate.service.SysMigratePubService;
import com.adtec.rdc.base.migrate.utils.MigrateUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@Service
public class SysMigratePubServiceImpl extends BaseServiceImpl<SysMigrateInfoMapper, SysMigrateInfo>
		implements SysMigratePubService {
	@Autowired
	private SysMigrateInfoMapper mapper;
	@Autowired
	private BaseDBConfig baseDBConfig;

	@Override
	public MigrateExport downLoad(List<String> ids) {
		if (ids == null || ids.size() == 0) {
			throw new ServiceException("请选择需要导出的数据！");
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		ids.forEach(id -> {
			String[] tempIds = id.split("_");
			if (tempIds.length == 2) {
				if (!map.containsKey(tempIds[0])) {
					map.put(tempIds[0], new ArrayList<String>());
				}
				map.get(tempIds[0]).add(tempIds[1]);
			}
		});

		MigrateExport export = new MigrateExport(new ArrayList<MigrateTable>());
		if(map.size() == 0) {
			throw new ServiceException("请选择需要导出的数据！");
		}
		List<SysMigrateInfo> migList = mapper.selectBatchIds(map.keySet());
		Map<String, MigrateTable> tabMap = new HashMap<String, MigrateTable>();
		Map<String, Map<String, Map<String, Object>>> tabDataMap = new HashMap<String, Map<String, Map<String, Object>>>();

		migList.forEach(mig -> {
			List<String> list = map.get(mig.getMigId());
			if(list != null && list.size() >0) {
				exportDataResursive(mig, map.get(mig.getMigId()), tabMap, tabDataMap);
			}
		});
		if(tabMap.size() == 0) {
			throw new ServiceException("请选择需要导出的数据！");
		}
		for (String key : tabMap.keySet()) {
			MigrateTable tab = tabMap.get(key);
			tab.setDatas(new ArrayList<Map<String, Object>>());
			Map<String, Map<String, Object>> tabData = tabDataMap.get(key);
			for (String dataId : tabData.keySet()) {
				tab.getDatas().add(tabData.get(dataId));
			}
			export.getLists().add(tab);
		}

		return export;
	}

	/**
	 * 递归查询需要导出的数据
	 */
	public void exportDataResursive(SysMigrateInfo mig, List<String> ids, Map<String, MigrateTable> tabMap,
			Map<String, Map<String, Map<String, Object>>> tabDataMap) {
		MigrateTable tab = null;
		if (tabMap != null && tabMap.containsKey(mig.getMigId())) {
			tab = tabMap.get(mig.getMigId());
		} else {
			tab = MigrateUtils.getTableInfo(mig, baseDBConfig);
		}
		List<Map<String, Object>> result = MigrateUtils.listObject(tab, mig, ids, baseDBConfig);
		Map<String, Map<String, Object>> tabData = MigrateUtils.mapObject(tab, result);
		if (tabDataMap.containsKey(mig.getMigTabName())) {
			tabDataMap.get(mig.getMigTabName()).putAll(tabData);
		} else {
			tabDataMap.put(mig.getMigTabName(), tabData);
		}
		if(tabMap != null) {
			tabMap.put(mig.getMigTabName(), tab);
		}
//		和该资源有关的资源
		QueryWrapper<SysMigrateInfo> query = new QueryWrapper<SysMigrateInfo>();
		query.lambda().eq(SysMigrateInfo::getParentMigId, mig.getMigId());
		List<SysMigrateInfo> list = mapper.selectList(query);
		Map<String, List<String>> relMap = new HashMap<String, List<String>>();
		if (list != null && list.size() > 0 && result.size() > 0) {
			list.forEach(info -> {
				StringBuffer relKey = new StringBuffer();
				relKey.append(info.getMigRelId()).append(info.getMigRelType()).append(info.getMigRelValue());
				List<String> relIds = null;
				if (relMap.containsKey(relKey.toString())) {
					relIds = relMap.get(relKey.toString());
				} else {
					relIds = getRelIds(mig, info, result);
					relMap.put(relKey.toString(), relIds);
				}
				if (relIds.size() > 0) {
					exportDataResursive(info, relIds, tabMap, tabDataMap);
				}
			});
		}
	}

	/**
	 * 解析关联id
	 */
	@SuppressWarnings("rawtypes")
	private List<String> getRelIds(SysMigrateInfo mig, SysMigrateInfo info, List<Map<String, Object>> result) {
		List<String> relIds = new ArrayList<String>();
		for (Map<String, Object> map : result) {
			if (map.get(info.getMigRelId()) == null) {
				continue;
			}
			String temp = String.valueOf(map.get(info.getMigRelId()));
			if (MigrateConverTypeEnum.DEFAULT.getVal().equals(info.getMigConverType())) {
				relIds.add(temp);
			} else if (MigrateConverTypeEnum.LIST.getVal().equals(info.getMigConverType())) {
				String[] tempIds = temp.split(",");
				Collections.addAll(relIds, tempIds);
			} else if (MigrateConverTypeEnum.MAP.getVal().equals(info.getMigConverType())) {
				Map m = JSONObject.parseObject(temp, Map.class);
				String[] keys = info.getMigRelValue().split(",");
				for (String key : keys) {
					if (m.containsKey(key)) {
						relIds.add(String.valueOf(m.get(key)));
					}
				}
			} else if (MigrateConverTypeEnum.JSON.getVal().equals(info.getMigConverType())) {
				String[] keys = info.getMigRelValue().split(",");
				relIds.addAll(getJsonIds(map.get(info.getMigRelValue()), Arrays.asList(keys)));
			}

		}
		return relIds;
	}

	/**
	 * 递归解析json,获取需要的值
	 */
	private List<String> getJsonIds(Object object, List<String> list) {
		List<String> ids = new ArrayList<String>();
		if (object instanceof JSONArray) {
			JSONArray jsArray = (JSONArray) object;
			for (Object object2 : jsArray) {
				ids.addAll(getJsonIds(object2, list));
			}
		} else if (object instanceof JSONObject) {
			JSONObject jsObject = (JSONObject) object;
			Set<String> it = jsObject.keySet();
			for (String key : it) {
				Object tempObject = jsObject.get(key);
				if (tempObject instanceof JSONArray)
					ids.addAll(getJsonIds((JSONArray) tempObject, list));
				else if (tempObject instanceof JSONObject)
					ids.addAll(getJsonIds((JSONObject) tempObject, list));
				else if (list.contains(key))
					ids.add(tempObject.toString());
			}
		}
		return ids;
	}

	@Override
	public Boolean upload(MigrateExport migrateExport) {
		if (migrateExport == null) {
			throw new ServiceException("数据为空，请检查！");
		}
		List<MigrateTable> mig_tabs = migrateExport.getLists();
		mig_tabs.forEach(migTab -> {
			uploadMigTabData(migTab);
		});

		return Boolean.TRUE;
	}

	private void uploadMigTabData(MigrateTable migTab) {
		String onceSql = MigrateUtils.getOnceSql(migTab);
		List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
		if (migTab.getPks() == null) {
			migTab.getDatas().forEach(mapVo -> {
				insertList.add(mapVo);
			});
		} else {
			migTab.getDatas().forEach(mapVo -> {
				if (MigrateUtils.isExistNode(migTab, mapVo, onceSql, baseDBConfig)) {
					updateList.add(mapVo);
				} else {
					insertList.add(mapVo);
				}
			});
		}
		MigrateUtils.batchInsertOrUpdate(migTab, insertList, updateList, baseDBConfig);
	}

	@Override
	public List<TreeNode> getAllTreeNodes() {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		QueryWrapper<SysMigrateInfo> query = new QueryWrapper<SysMigrateInfo>();
		query.lambda().eq(SysMigrateInfo::getParentMigId, CommonConstants.TREE_ROOT);
		List<SysMigrateInfo> list = mapper.selectList(query);
		list.forEach(info -> {
			treeNodes.add(MigrateUtils.getNode(info, baseDBConfig));
		});
		return treeNodes;
	}

}
