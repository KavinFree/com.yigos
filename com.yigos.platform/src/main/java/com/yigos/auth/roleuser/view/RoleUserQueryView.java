package com.yigos.auth.roleuser.view;

import com.yigos.framework.model.PageModel;

public class RoleUserQueryView extends PageModel{
	private String userCode;
	private String userName;
	private String userPhone;
	private String roleCode;
	private String roleName;
	private Integer sysDelFlag;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getSysDelFlag() {
		return sysDelFlag;
	}
	public void setSysDelFlag(Integer sysDelFlag) {
		this.sysDelFlag = sysDelFlag;
	}
	
}
