package com.adtec.rdc.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.anno.mapper.SysAnnoInfoMapper;
import com.adtec.rdc.base.anno.model.po.SysAnnoInfo;
import com.adtec.rdc.base.user.mapper.PersonPageMapper;
import com.adtec.rdc.base.user.mapper.SysMessageInfoMapper;
import com.adtec.rdc.base.user.model.bo.MessageInfo;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.adtec.rdc.base.user.service.PersonPageService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class PersonPageServiceImpl  extends ServiceImpl<PersonPageMapper, MessageInfo> implements PersonPageService {
	@Autowired
	private PersonPageMapper mapper;
	@Autowired
	private SysAnnoInfoMapper annoMapper;
	@Autowired
	private SysMessageInfoMapper messageMapper;
	
	@Override
	public SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query) {
		mapper.pageByQuery(query);
	    return query;
	}

	@Override
	@Transactional
	public Boolean readMessage(String type, String ids, String userId) {
		List<String> idList = new ArrayList<String>();
		if(ids.indexOf(",")>-1) {
			String[] arrayIds = ids.split(",");
			for(String arrayId : arrayIds) {
				idList.add(arrayId);
			}
		}else {
			idList.add(ids);
		}
		if("anno".equals(type)) {
			readAnno(idList, userId);
    	}else if("mess".equals(type)) {
    		readMessage(idList, userId);
    	}
		return true;
	}
	
	private void readMessage(List<String> idList, String userId) {
		List<SysMessageInfo> messes = messageMapper.queryMessageListByUserIdAndIdList(userId, idList);
		for(SysMessageInfo mess : messes) {
			if(StringUtils.isEmpty(mess.getMessageStatus()) || "0".equals(mess.getMessageStatus())) {
				mess.setMessageStatus("1");
				messageMapper.updateById(mess);
			}
		}
	}

	private void readAnno(List<String> idList, String userId) {
		List<SysAnnoInfo> annos = annoMapper.queryAnnoListUserOper(userId);
		List<SysAnnoInfo> updates = new ArrayList<SysAnnoInfo>();
		for(SysAnnoInfo anno : annos) {
			if(idList.contains(anno.getAnnoId())) {
				//已经是已读状态的了 - remove
				if(!StringUtils.isEmpty(anno.getReadFlag()) && "1".equals(anno.getReadFlag())) {
					idList.remove(anno.getAnnoId());
					if(idList.size()==0) {
						break;
					}
				}
				//存在,但不是已读状态 - udpate (正常此种记录不存在)
				else {
					idList.remove(anno.getAnnoId());
					anno.setReadFlag("1");
					updates.add(anno);
				}
				if(idList.size()==0) {
					break;
				}
			}
		}
		if(updates.size()>0) {
			annoMapper.updateAnnoUserOper(updates);
		}
		if(idList.size()>0) {
			List<SysAnnoInfo> inserts = new ArrayList<SysAnnoInfo>(idList.size());
			for(String id : idList) {
				SysAnnoInfo anno = new SysAnnoInfo();
				anno.setAnnoId(id);
				anno.setUserId(userId);
				anno.setReadFlag("1");
				anno.setDeleFlag("0");
				inserts.add(anno);
			}
			annoMapper.insertAnnoUserOper(inserts);
		}
	}

	@Override
	@Transactional
	public Boolean deleteMessage(String type, String ids, String userId) {
		List<String> idList = new ArrayList<String>();
		if(ids.indexOf(",")>-1) {
			String[] arrayIds = ids.split(",");
			for(String arrayId : arrayIds) {
				idList.add(arrayId);
			}
		}else {
			idList.add(ids);
		}
		if("anno".equals(type)) {
			deleteAnno(idList, userId);
    	}else if("mess".equals(type)) {
    		deleteMessage(idList, userId);
    	}
		return true;
	}

	private void deleteMessage(List<String> idList, String userId) {
		List<SysMessageInfo> messes = messageMapper.queryMessageListByUserIdAndIdList(userId, idList);
		for(SysMessageInfo mess : messes) {
			if(StringUtils.isEmpty(mess.getDelFlag()) || "0".equals(mess.getDelFlag())) {
				mess.setDelFlag("1");
				messageMapper.updateById(mess);
			}
		}
	}

	private void deleteAnno(List<String> idList, String userId) {
		List<SysAnnoInfo> annos = annoMapper.queryAnnoListUserOper(userId);
		List<SysAnnoInfo> updates = new ArrayList<SysAnnoInfo>();
		for(SysAnnoInfo anno : annos) {
			if(idList.contains(anno.getAnnoId())) {
				//已经是删除状态的了 - remove
				if(!StringUtils.isEmpty(anno.getDeleFlag()) && "1".equals(anno.getDeleFlag())) {
					idList.remove(anno.getAnnoId());
				}
				//存在,但不是删除状态 - udpate
				else {
					idList.remove(anno.getAnnoId());
					anno.setDeleFlag("1");
					updates.add(anno);
				}
				if(idList.size()==0) {
					break;
				}
			}
		}
		if(updates.size()>0) {
			annoMapper.updateAnnoUserOper(updates);
		}
		if(idList.size()>0) {
			List<SysAnnoInfo> inserts = new ArrayList<SysAnnoInfo>(idList.size());
			for(String id : idList) {
				SysAnnoInfo anno = new SysAnnoInfo();
				anno.setAnnoId(id);
				anno.setUserId(userId);
				anno.setReadFlag("0");
				anno.setDeleFlag("1");
				inserts.add(anno);
			}
			annoMapper.insertAnnoUserOper(inserts);
		}
	}
}
