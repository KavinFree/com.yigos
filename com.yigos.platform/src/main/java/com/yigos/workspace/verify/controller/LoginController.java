package com.yigos.workspace.verify.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.user.view.SysUserView;
import com.yigos.framework.common.util.datetime.DateTimeUtil;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;
import com.yigos.workspace.verify.service.itf.LoginService;
import com.yigos.workspace.verify.view.LoginView;

@Controller
public class LoginController extends BaseControllerImpl{
	
	@Autowired
	private LoginService service;
	
	@RequestMapping
	public void index(Model model) throws Exception {
		
	}
	
	@RequestMapping
	public void chechUser(HttpServletRequest request, Model model, 
			SysUserView view) throws Exception {
		String securityCodeImg = (String)request.getSession().getAttribute(DataDict.SECURITY_CODE);
		LoginView loginView = this.service.chechUser(securityCodeImg, view);
		if(loginView.isFlag()){
			HttpSession session = request.getSession();
			session.setAttribute(DataDict.SESSION_ID, view.getId());
		}
		this.writeJsonToResponse(model, loginView.isFlag(), 
			DataDict.NORMAL, loginView, 1);
	}
	
	@RequestMapping
	public void securityCode(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String securityCode = DateTimeUtil.randomCode(4);
		HttpSession session = request.getSession();
		// 在登录验证的时候会首先检查验证码是否输入正确
		session.setAttribute(DataDict.SECURITY_CODE, securityCode);
		BufferedImage buffimg = new BufferedImage(60, 20,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = buffimg.createGraphics();
		this.service.securityCode(securityCode, g);
		// 将图像输出到servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffimg, "png", sos);
		sos.close();
		g.dispose();
	}
	
	@RequestMapping
	public void exit(HttpServletRequest request, Model model) throws Exception{
		HttpSession session = request.getSession();
		if(session!=null){
		    session.invalidate();
		}
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "/system/manager/login.html", 1);
	}
	
}
