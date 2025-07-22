package com.littlelee.base.user.model.query;

import com.littlelee.base.common.model.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author: littlelee
 * @date: 2018/11/5 14:02
 */
@Data
public class SysUserVoQuery extends Page<SysUserVo> {
	
	private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 登录名
     */
    private String loginName;

    private String delFlag;
    
    /**
     * 租户id
     */
    private String appId;

}
