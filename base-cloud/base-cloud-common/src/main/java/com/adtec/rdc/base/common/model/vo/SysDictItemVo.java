package com.adtec.rdc.base.common.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典明细表
 * </p>
 *
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
@Data
public class SysDictItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

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
