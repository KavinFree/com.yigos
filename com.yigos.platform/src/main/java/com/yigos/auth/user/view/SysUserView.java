package com.yigos.auth.user.view;

import java.util.List;
import com.yigos.auth.entity.SysUser;

public class SysUserView extends SysUser{

	private static final long serialVersionUID = 1L;
	private String securityCode;
	private List<String> ids;
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
