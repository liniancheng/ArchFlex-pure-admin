package com.adtec.rdc.base.user.model.query;

import java.time.LocalDateTime;

import com.adtec.rdc.base.user.model.po.SysUserPwdLogInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2021-02-07 14:34:42
 */
@Data
public class SysUserPwdLogInfoQuery extends Page<SysUserPwdLogInfo> {

        /**
     * 主键ID
     */
    private String logId;
        /**
     * 登录名
     */
    private String loginName;
        /**
     * 密码
     */
    private String loginPwd;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
