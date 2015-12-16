package com.yigos.workspace.verify.service.impl;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.yigos.auth.entity.SysUser;
import com.yigos.auth.user.view.SysUserView;
import com.yigos.framework.common.util.mapping.ConfigMapping;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.service.BaseServiceImpl;
import com.yigos.workspace.verify.service.itf.LoginService;
import com.yigos.workspace.verify.view.LoginView;

@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService{

	@Override
	public void securityCode(String securityCode, Graphics2D g2d) {
		Random rd = new Random();
		Color lineColor = new Color(1.0F, 0.75F, 0.0F, 0.45F);
		// 干扰线
		g2d.setColor(lineColor);
		for (int i = 0; i < 6; i++) {
			int x1 = rd.nextInt(60);
			int x2 = rd.nextInt(60);
			int y1 = rd.nextInt(20);
			int y2 = rd.nextInt(20);
			g2d.drawLine(x1, y1, x2, y2);
		}
		// 显示随机码
		Font font = new Font("times new roman", Font.PLAIN, 19);
		g2d.setFont(font);
		g2d.setColor(new Color(128,122,123,42));
		g2d.setColor(new Color(255,0,0));
		g2d.drawString(securityCode, 5, 15);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
		g2d.setStroke(new BasicStroke(1));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}

	@Override
	public LoginView chechUser(String securityCodeImg, SysUserView view) {
		LoginView loginView =  new LoginView();
		loginView.setFlag(false);
		loginView.setLink("");
		String securityCode = ConfigMapping.getProperty("workspace.verify.Login.securityCode");
		if(StringUtils.isNotBlank(securityCode)){
			securityCode = securityCodeImg;//debug 模式
		}else{
			securityCode = view.getSecurityCode();//release 模式
		}
		if(StringUtils.equalsIgnoreCase(securityCodeImg, securityCode)){
			DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
			// 加入查询条件
			criteria.add(Restrictions.eq("code", view.getCode()));
			@SuppressWarnings("unchecked")
			List<SysUser> list = (List<SysUser>) this.dao.findByCriteria(criteria);
			if(list !=null && list.size()==1){
				SysUser user = list.get(0);
				if(StringUtils.isNotBlank(view.getPassword())){
					if(StringUtils.equals(user.getPassword(), view.getPassword())
						&& StringUtils.equals(user.getCode(), view.getCode())){
						if(null!=user.getSysDelFlag() 
						&& user.getSysDelFlag()==DataDict.SYS_DEL_FLAG_ENABLE){
							view.setId(user.getId());
							loginView.setFlag(true);
							loginView.setMsg("用户登陆成功！");
							loginView.setLink("/workspace/system/page.html");
						}else{
							loginView.setMsg("此用户已经被锁定！");
							loginView.setLink("codeMsg");
						}
					}else{
						loginView.setMsg("此用户密码不正确！");
						loginView.setLink("passwordMsg");
					}
				}else{
					loginView.setMsg("此用户密码不能留空！");
					loginView.setLink("passwordMsg");
				}
			}else{
				loginView.setMsg("此用户编号不存在！");
				loginView.setLink("codeMsg");
			}
		}else{
			loginView.setMsg("验证码不正确！");
			loginView.setLink("securityCodeImgMsg");
		}
		return loginView;
	}

}
