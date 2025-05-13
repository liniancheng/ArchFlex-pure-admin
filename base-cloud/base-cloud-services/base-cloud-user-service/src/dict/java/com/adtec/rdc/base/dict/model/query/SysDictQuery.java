package com.adtec.rdc.base.dict.model.query;

import java.util.Date;

import com.adtec.rdc.base.dict.model.po.SysDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
@Data
public class SysDictQuery extends Page<SysDict> {

        /**
     * 
     */
    private String id;
        /**
     * 字典名称
     */
    private String dictName;
        /**
     * 字典编码
     */
    private String dictCode;
        /**
     * 描述
     */
    private String description;
        /**
     * 删除状态
     */
    private Integer delFlag;
        /**
     * 创建时间
     */
    private Date createTime;
        /**
     * 字典类型
     */
    private String type;
    
}
