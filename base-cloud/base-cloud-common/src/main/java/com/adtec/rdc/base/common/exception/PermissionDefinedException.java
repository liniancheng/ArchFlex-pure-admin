package com.adtec.rdc.base.common.exception;

/**
 * @author: JTao
 * @date: 2018/10/12 11:03
 * @description:
 */
public class PermissionDefinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PermissionDefinedException() {
        super("权限不足，访问失败");
    }
}
