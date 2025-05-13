package com.adtec.rdc.web.antd.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Vue树节点
 * @author JTao
 *
 */
@Data
@Accessors(chain = true)
public class VueTreeNode {
	
	public VueTreeNode() {
		this.selectable = true;
		this.checkable = false;
	}
	
    /**
     * 主键
     */
    private String key;

    /**
     * 显示内容
     */
    private String title;

    /**
     * 节点值
     */
    private String value;

    /**
     * 是否叶子节点
     */
    private boolean isIsLeaf;

    /**
     * 是否禁用
     */
    private boolean disabled;
    
    /**
     * 是否选中
     */
    private boolean checked;
    
    /**
     * 是否可选
     */
    private boolean selectable;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 上级id
     */
    private String parentId;
    
    /**
     * 子节点
     */
    private List<VueTreeNode> children;
    
    /**
     * 打开连接
     */
    private String url;
    
    /**
     * vue组件
     */
    private String component;
    
    /**
     * 显示图标
     */
    private String icon;
    
    private boolean checkable;
}
