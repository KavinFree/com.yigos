package com.yigos.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the sys_role_user database table.
 * 
 */
@Entity
@Table(name="sys_role_user")
@NamedQuery(name="SysRoleUser.findAll", query="SELECT s FROM SysRoleUser s")
public class SysRoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int archive;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String createUser;

	private int delFlag;

	private String idRole;

	private String idUser;

	private int source;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	private String updateUser;

	private BigInteger version;

	public SysRoleUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getArchive() {
		return this.archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getIdRole() {
		return this.idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public int getSource() {
		return this.source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public BigInteger getVersion() {
		return this.version;
	}

	public void setVersion(BigInteger version) {
		this.version = version;
	}

}