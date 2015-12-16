package com.yigos.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.framework.controller.BaseControllerImpl;
import com.yigos.workspace.service.itf.SystemService;

@Controller
public class SystemController extends BaseControllerImpl{
	
	@Autowired
	private SystemService service;
	
	@RequestMapping
	public void page() throws Exception{
		
	}
	
	@RequestMapping
	public void menu() throws Exception{
		
	}
}
