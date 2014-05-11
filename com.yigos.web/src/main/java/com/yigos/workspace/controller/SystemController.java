package com.yigos.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.framework.controller.BaseController;
import com.yigos.workspace.service.itf.SystemService;

@Controller
public class SystemController extends BaseController{
	
	@Autowired
	private SystemService service;
	
	@RequestMapping
	public String index() throws Exception{
		return super.pagePath("workspace.system.index");
	}
	
}
