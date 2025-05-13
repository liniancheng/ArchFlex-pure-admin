package com.adtec.rdc.base.layout.model.query;

import com.adtec.rdc.base.layout.model.po.SysLayoutItemRel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2020-08-25 15:08:26
 */
@Data
public class SysLayoutItemRelQuery extends Page<SysLayoutItemRel> {

    /**
     * 布局id
     */
    private String layId;
    /**
     * 数据项ID
     */
    private String itemId;
    /**
     * 列编号
     */
    private Integer relX;
    /**
     * 行编号
     */
    private Integer relY;
    /**
     * 初始宽度
     */
    private Integer relW;
    /**
     * 初始高度
     */
    private Integer relH;

}
