package com.littlelee.base.auth.query;

import com.littlelee.base.auth.controller.AuthController.TokenVo;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author: littlelee
 * @date: 2018/11/15 15:43
 * @description: Spring Authorization Server 6.x 分页查询参数
 */
@Data
public class OAuth2AccessTokenQuery {

    /** 每页条数 */
    private Integer size = 10;

    /** 当前页（从 1 开始） */
    private Integer current = 1;

    /** 总页数，可忽略，由业务层计算 */
    private Integer pages = 1;

    /** 总记录数 */
    private Integer total = 0;

    /** 当前页记录列表 */
    private List<TokenVo> records = Collections.emptyList();

    /* -------------- 辅助计算 -------------- */

    public Integer getOffset() {
        return (current - 1) * size;
    }

    public Integer getLimit() {
        return size;
    }
}