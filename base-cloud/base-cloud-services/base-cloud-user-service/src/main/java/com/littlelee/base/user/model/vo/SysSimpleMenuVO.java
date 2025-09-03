package com.littlelee.base.user.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表(菜单与按钮)
 * </p>
 *
 * @author: liniancheng
 * @since 2025-09-03
 */
@Data
@Accessors(chain = true)
public class SysSimpleMenuVO {

    private String id;

    private String title;

    private String menuType;

    private String parentId;

}
