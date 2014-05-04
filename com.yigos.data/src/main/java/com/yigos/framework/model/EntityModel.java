package com.yigos.framework.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EntityModel {
	
	@Column(name="delFlag", length=2, nullable=true)//scale=2, precision=0, 
	protected Integer delFlag;
	@Column(name="createUser", length=20)
	protected String createUser;
	@Column(name="createDate")
	@Temporal(TemporalType.DATE)
	protected Date createDate;
	@Column(name="updateUser", length=20)
	protected String updateUser;
	@Column(name="updateDate")
	@Temporal(TemporalType.DATE)
	protected Date updateDate;
	@Version
	@Column(name="version", length=20, nullable=true)
	protected Integer version;
	@Column(name="source", length=2)
	protected Integer source;
	@Column(name="archive", length=2)
	protected Integer archive;
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getArchive() {
		return archive;
	}
	public void setArchive(Integer archive) {
		this.archive = archive;
	}
	
}
