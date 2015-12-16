package com.yigos.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_role_user")
public class SysRoleUser extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="idUser", length=50)
	private String idUser;
	
	@Column(name="idRole", length=50)
	private String idRole;
	
	@Column(name="remark", length=3000)
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
