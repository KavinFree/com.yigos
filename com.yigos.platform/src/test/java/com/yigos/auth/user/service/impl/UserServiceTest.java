package com.yigos.auth.user.service.impl;


import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.google.gson.Gson;
import com.yigos.auth.entity.SysUser;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.SysUserView;
import com.yigos.junit.BaseJUnit;

public class UserServiceTest extends BaseJUnit {
	
	@Resource
	private UserService userService;
	
	@Test
	public void findUserByPK(){
		SysUserView userView = new SysUserView();
		userView.setCode("kavin@yigos.com");
		
		this.userService.saveUser(userView);
	}
	
	@Test
	@Rollback(false)
	public void saveUser(){
		for(int i=1;i<10;i++){
			SysUserView userView = new SysUserView();
			userView.setCode("kavin"+i+"@yigos.com");
			userView.setPassword("96e79218965eb72c92a549dd5a330112");
			Gson gson = new Gson();
			userView.setRemark(gson.toJson(userView));
//			userView.setVersion(new BigDecimal(1L));//9223372036854775807L
//			userView.setDelFlag(0);
			this.userService.saveUser(userView);
		}
	}
	
	@Test
	public void updateUser(){
		SysUser user = this.userService.findUserByPK("4028b881453cbaa701453cbab0a70000");
		user.setName("kavins22@yigos.com");
		
//		user = this.userService.updateUser(user);
	}
	
}