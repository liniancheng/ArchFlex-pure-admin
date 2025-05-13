package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户组
 * </p>
 *
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
@Data
@Accessors(chain = true)
public class SysUserGroupInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户组id
     */
    @TableId(value = "group_id", type = IdType.UUID)
    private String groupId;
    
    /**
	 * 租户id
	 */
	@TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
	private String appId;
	
    /**
     * 用户组名称
     */
    @TableField(value = "group_name", strategy = FieldStrategy.IGNORED) 
    private String groupName;
    /**
     * 用户组描述
     */
    @TableField(value = "group_rmk", strategy = FieldStrategy.IGNORED) 
    private String groupRmk;

    /**
     * 绑定的用户id集合
     */
    @TableField(exist = false)
    private List<String> userIds;
    
    /**
     * 取消绑定的用户id集合
     */
    @TableField(exist = false)
    private List<String> unUserIds;
    
    /**
     * 绑定的机构号集合
     */
    @TableField(exist = false)
    private List<String> branchNos;
}