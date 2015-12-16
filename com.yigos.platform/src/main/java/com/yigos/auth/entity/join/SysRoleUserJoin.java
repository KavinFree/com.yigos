package com.yigos.auth.entity.join;

import java.io.Serializable;
import javax.persistence.*;
import com.yigos.auth.entity.SysRole;
import com.yigos.auth.entity.SysUser;
import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_role_user")
@NamedQuery(name="SysRoleUserJoin.findAll", query="SELECT s FROM SysRoleUserJoin s")
public class SysRoleUserJoin extends EntityModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue
	private String id;

	@Column(name="remark", length=3000)
	private String remark;

	//bi-directional many-to-one association to SysRole
	@ManyToOne
	@JoinColumn(name="idRole")
	private SysRole sysRole;

	//bi-directional many-to-one association to SysUser
	@ManyToOne
	@JoinColumn(name="idUser")
	private SysUser sysUser;

	public SysRoleUserJoin() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SysRole getSysRole() {
		return this.sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}