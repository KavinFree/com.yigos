package com.yigos.auth.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.UserView;
import com.yigos.framework.controller.BaseController;

@Controller
public class UserController extends BaseController{
	
	@Autowired
	private UserService service;
	
	@RequestMapping
	public String index(UserView view) throws Exception{
		/*
		this.service.findUserByPK(view.getId());
		this.service.saveUser(view);
		*/
		return "index";
	}
	
	@RequestMapping
	public void list(HttpServletRequest request) throws Exception{

	}
	
}
