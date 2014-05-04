package com.yigos.auth.user.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;

import com.yigos.auth.entity.User;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.UserView;
import com.yigos.junit.BaseJUnit;

public class UserServiceTest extends BaseJUnit {
	
	@Resource
	private UserService userService;
	@Test
	public void findUserByPK(){
		UserView userView = new UserView();
		userView.setCode("kavin@yigos.com");
		this.userService.saveUser(userView);
	}
	
	@Test
	public void saveUser(){
		UserView userView = new UserView();
		userView.setCode("kavin@yigos.com");
//		userView.setVersion(new BigDecimal(1L));//9223372036854775807L
//		userView.setDelFlag(0);
		this.userService.saveUser(userView);
	}
	
	@Test
	public void updateUser(){
		User user = this.userService.findUserByPK("4028b881453cbaa701453cbab0a70000");
		user.setName("kavins22@yigos.com");
		
		user = this.userService.updateUser(user);
		
	}
	
}