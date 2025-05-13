package com.adtec.rdc.base.user.model.query;

import java.util.List;

import com.adtec.rdc.base.common.model.vo.SysUserVo;
import com.adtec.rdc.base.user.model.po.SysUserGroupInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
@Data
public class SysUserGroupInfoQuery extends Page<SysUserGroupInfo> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  用户组id
	 */
	private String groupId;
	/**
	 *  租户id
	 */
	private String appId;
    /**
     * 用户组名称
     */
    private String groupName;
    /**
     * 用户组描述
     */
    private String groupRmk;
    
    private List<SysUserVo> sysUserVoList;
    
    private List<String> branchNos;

    
}
