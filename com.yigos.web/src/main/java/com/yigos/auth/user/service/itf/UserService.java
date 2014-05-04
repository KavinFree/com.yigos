package com.yigos.auth.user.service.itf;

import com.yigos.auth.entity.User;
import com.yigos.auth.user.view.UserView;

public interface UserService {
	
	User findUserByPK(String id);
	User saveUser(UserView userView);
	User deleteUser(UserView userView);
	User updateUser(User user);
}
