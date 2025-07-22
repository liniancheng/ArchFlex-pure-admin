package com.littlelee.base.common.base.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseHttpController {
	@Autowired
	protected HttpServletRequest request;
}
