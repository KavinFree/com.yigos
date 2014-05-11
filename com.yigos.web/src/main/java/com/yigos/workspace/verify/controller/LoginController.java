package com.yigos.workspace.verify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.framework.controller.BaseController;


public class LoginController  extends BaseController{
	
	@RequestMapping
	public String index() throws Exception{
		return super.pagePath("workspace.verify.login");
	}
	
	@RequestMapping
	public void chechUser() throws Exception{
		
	}
}
