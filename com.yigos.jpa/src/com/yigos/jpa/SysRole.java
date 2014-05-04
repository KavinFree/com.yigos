package com.yigos.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the sys_role database table.
 * 
 */
@Entity
@Table(name="sys_role")
@NamedQuery(name="SysRole.findAll", query="SELECT s FROM SysRole s")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int archive;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String createUser;

	private int delFlag;

	private String name;

	private String remark;

	private int source;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	private String updateUser;

	private BigInteger version;

	public SysRole() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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