package com.yigos.workspace.verify.service.itf;

import java.awt.Graphics2D;

import com.yigos.auth.user.view.SysUserView;
import com.yigos.workspace.verify.view.LoginView;

public interface LoginService {
	void securityCode(String securityCode,Graphics2D g2d);
	
	LoginView chechUser(String securityCodeImg, SysUserView view);
}
