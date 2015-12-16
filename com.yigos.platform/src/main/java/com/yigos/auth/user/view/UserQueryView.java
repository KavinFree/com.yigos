package com.yigos.auth.user.view;

import com.yigos.framework.model.PageModel;

public class UserQueryView extends PageModel{
	private String code;
	private String name;
	private String phone;
	protected Integer sysDelFlag;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSysDelFlag() {
		return sysDelFlag;
	}
	public void setSysDelFlag(Integer sysDelFlag) {
		this.sysDelFlag = sysDelFlag;
	}
	
}
