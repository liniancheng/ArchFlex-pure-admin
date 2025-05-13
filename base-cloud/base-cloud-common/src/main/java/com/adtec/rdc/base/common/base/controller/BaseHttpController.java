package com.adtec.rdc.base.common.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseHttpController {
	@Autowired
	protected HttpServletRequest request;
}
