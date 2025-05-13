package com.adtec.rdc.base.layout.model.query;

import java.time.LocalDateTime;
import java.util.List;

import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2020-08-25 15:04:09
 */
@Data
public class SysLayoutInfoQuery extends Page<SysLayoutInfo> {

    /**
     * 主键
     */
    private String layId;
    /**
     * 布局名称
     */
    private String layName;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 布局类型，0-默认布局；1-权限布局；2-自定义布局
     */
    private String layType;
    /**
     * 应用ID
     */
    private Integer layLevel;
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
