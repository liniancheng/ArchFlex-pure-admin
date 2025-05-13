package com.adtec.rdc.base.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.user.mapper.SysGroupBranchRelMapper;
import com.adtec.rdc.base.user.mapper.SysUserGroupInfoMapper;
import com.adtec.rdc.base.user.mapper.SysUserGroupRelMapper;
import com.adtec.rdc.base.user.model.po.SysGroupBranchRel;
import com.adtec.rdc.base.user.model.po.SysUserGroupInfo;
import com.adtec.rdc.base.user.model.po.SysUserGroupRel;
import com.adtec.rdc.base.user.model.query.SysUserGroupInfoQuery;
import com.adtec.rdc.base.user.service.SysUserGroupInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
@Service
public class SysUserGroupInfoServiceImpl extends BaseServiceImpl<SysUserGroupInfoMapper, SysUserGroupInfo> implements SysUserGroupInfoService {

	@Autowired
	private SysUserGroupInfoMapper mapper;
	@Autowired
	private SysGroupBranchRelMapper sysGroupBranchRelMapper;
	@Autowired
	private SysUserGroupRelMapper sysUserGroupRelMapper;

	@Override
	public SysUserGroupInfoQuery pageByQuery(SysUserGroupInfoQuery query) {
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public boolean deleteById(String groupId) {
		mapper.deleteById(groupId);
		deleteBindGroupWithBranch(groupId);
		deleteBindGroupWithUser(groupId);
		return Boolean.TRUE;
	}

	/**
	 * 删除用户组绑定的机构
	 * @param groupId
	 */
	private void deleteBindGroupWithBranch(String groupId) {
		QueryWrapper<SysGroupBranchRel> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(SysGroupBranchRel::getGroupId, groupId);
		sysGroupBranchRelMapper.delete(wrapper);
	}
	
	private void deleteBindGroupWithUser(String groupId) {
		QueryWrapper<SysUserGroupRel> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(SysUserGroupRel::getGroupId, groupId);
		sysUserGroupRelMapper.delete(wrapper);
	}

	@Override
	public boolean saveGroup(SysUserGroupInfo group) {
		Integer count = mapper.getGroupByName(group.getGroupName());
		if(count>0) {
			throw new ServiceException(ErrorCodeEnum.GROUP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.GROUP_RE_NAME.getMessage());
		}
		mapper.insert(group);
		bindWithBranchs(group);
		return Boolean.TRUE;
	}

	private void bindWithBranchs(SysUserGroupInfo group) {
		if(group.getBranchNos()==null || group.getBranchNos().size()==0) {
			return;
		}
		group.getBranchNos().forEach(branchNo -> {
			SysGroupBranchRel sysGroupBanchRel = new SysGroupBranchRel();
			sysGroupBanchRel.setGroupId(group.getGroupId());
			sysGroupBanchRel.setBranchNo(branchNo);
			sysGroupBranchRelMapper.insert(sysGroupBanchRel);
		});

	}

	@Override
	public boolean updateGroupById(SysUserGroupInfo group) {
		if(mapper.getGroupByNameAndId(group.getGroupName(), group.getGroupId())>0) {
			throw new ServiceException(ErrorCodeEnum.GROUP_RE_NAME.getErrorCode(),
					ErrorCodeEnum.GROUP_RE_NAME.getMessage());
		}
		mapper.updateById(group);
		deleteGroupBranchRel(group);
		bindWithBranchs(group);
		return Boolean.TRUE;
	}

	private void deleteGroupBranchRel(SysUserGroupInfo group) {
		List<String> branchNos = group.getBranchNos();
		if(branchNos !=null && branchNos.size()>0) {
			sysGroupBranchRelMapper.removeByIds(branchNos, group.getGroupId());
		}else {
			deleteBindGroupWithBranch(group.getGroupId());
		}
	}

	@Override
	public SysUserGroupInfoQuery findGroupListById(String groupId) {
		SysUserGroupInfoQuery group = mapper.loadGroupById(groupId);
		QueryWrapper<SysGroupBranchRel> branchQueryWrapper = new QueryWrapper<>();
		branchQueryWrapper.eq("group_id", group.getGroupId());
		List<SysGroupBranchRel> branchRel = sysGroupBranchRelMapper.selectList(branchQueryWrapper);
		List<String> branchNos = branchRel.stream()
				.map(SysGroupBranchRel::getBranchNo)
				.collect(Collectors.toList());
		group.setBranchNos(branchNos);
		return group;

	}
}
