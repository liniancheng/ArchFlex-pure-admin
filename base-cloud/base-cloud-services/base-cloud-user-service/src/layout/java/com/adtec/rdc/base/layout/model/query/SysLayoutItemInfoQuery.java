package com.adtec.rdc.base.layout.model.query;

import java.time.LocalDateTime;
import java.util.List;

import com.adtec.rdc.base.layout.model.po.SysLayoutItemInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2020-08-25 15:17:06
 */
@Data
public class SysLayoutItemInfoQuery extends Page<SysLayoutItemInfo> {

    /**
     * 主键
     */
    private String itemId;
    /**
     * 布局名称
     */
    private String itemName;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 组件路径
     */
    private String itemComp;
    /**
     * 最小宽度
     */
    private Integer minWidth;
    /**
     * 最小高度
     */
    private Integer minHeight;
    /**
     * 最大宽度
     */
    private Integer maxWidth;
    /**
     * 最大高度
     */
    private Integer maxHeight;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;
    
    private List<String> roleIds;

}
