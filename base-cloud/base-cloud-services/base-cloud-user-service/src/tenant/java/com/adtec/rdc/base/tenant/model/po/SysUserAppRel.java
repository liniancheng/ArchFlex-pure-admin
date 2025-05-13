package com.adtec.rdc.base.tenant.model.po;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysUserAppRel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 一用户id
	 */
	private String userId;
	/**
	 * 租户id
	 */
	private String appId;
	
	/**
     * 关联租户集合
     */
    @TableField(exist = false)
    private List<String> appIds;
	
}
