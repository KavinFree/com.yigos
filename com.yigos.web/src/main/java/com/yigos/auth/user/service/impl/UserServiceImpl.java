package com.yigos.auth.user.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yigos.auth.entity.User;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.UserView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.dao.itf.BaseDao;
import com.yigos.framework.service.impl.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	@Autowired
	private BaseDao dao;
	
	@Override
	public User findUserByPK(String id) {
		User user = this.dao.find(User.class, id);
		return user;
	}

	@Override
	public User saveUser(UserView userView) {
		User user = new User();
		BeanUtils.copyProperties(userView, user);
		return  this.dao.save(user);
	}

	@Override
	public User deleteUser(UserView userView) {
		User user = findUserByPK(userView.getId());
		user.setDelFlag(0);
		this.updateUser(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		user.setDelFlag(DataDict.ENABLE_1);
		return this.dao.update(user);
	}
	
}
