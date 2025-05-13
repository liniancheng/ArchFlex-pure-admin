package com.adtec.rdc.base.dict.model.query;

import java.util.Date;

import com.adtec.rdc.base.dict.model.po.SysDictItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
@Data
public class SysDictItemQuery extends Page<SysDictItem> {

        /**
     * 
     */
    private String id;
        /**
     * 字典ID
     */
    private String dictId;
        /**
     * 字典项文本
     */
    private String itemText;
        /**
     * 字典项值
     */
    private String itemValue;
        /**
     * 描述
     */
    private String description;
        /**
     * 排序
     */
    private Integer sortOrder;
        /**
     * 状态（1启用 0不启用）
     */
    private Integer status;
        /**
     * 创建时间
     */
    private Date createTime;
    
}
