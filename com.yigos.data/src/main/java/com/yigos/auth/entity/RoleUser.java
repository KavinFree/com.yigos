package com.yigos.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_role_user")//,uniqueConstraints = {@UniqueConstraint(columnNames={"idUser", "idRole"})}
public class RoleUser extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=40)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="idUser")
	private String idUser;
	
	@Column(name="idRole")
	private String idRole;

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
	
}
