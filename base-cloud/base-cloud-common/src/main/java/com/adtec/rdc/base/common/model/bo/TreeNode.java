package com.adtec.rdc.base.common.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 类名：TreeNode</br>
 * 模块：</br>
 * 说明：</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright 北京先进数通信息技术股份公司版权所有
 * @since 2019/11/11
 */
@Data
@Accessors(chain = true)
public class TreeNode {
	
	public TreeNode() {
		this.selectable = true;
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
    private boolean isLeaf;

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
    private List<TreeNode> children;
}
