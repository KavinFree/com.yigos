package com.yigos.framework.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yigos.framework.annotation.DateTimeJsonSerializer;
import com.yigos.framework.constant.DataDict;

@MappedSuperclass
public abstract class EntityModel {
	
	@Column(name="sysDelFlag", length=11)//, nullable=false
	protected Integer sysDelFlag;
	
	@Column(name="sysCreateUser", length=50)
	protected String sysCreateUser;
	
	@Column(name="sysCreateDateTime")
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	protected Timestamp sysCreateDateTime;
	
	@Column(name="sysUpdateUser", length=50)
	protected String sysUpdateUser;
	
	@Column(name="sysUpdateDateTime")
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	protected Timestamp sysUpdateDateTime;
	
	@Version
	@Column(name="sysVersion", length=20)//, nullable=false
	protected Integer sysVersion;
	
	@Column(name="sysSource", length=11)//, nullable=false
	protected Integer sysSource;
	
	@Column(name="sysArchive", length=11)//, nullable=false
	protected Integer sysArchive;
	
	///------set get
	public Integer getSysDelFlag() {
		return sysDelFlag;
	}
	public void setSysDelFlag(Integer sysDelFlag) {
		if(sysDelFlag!=null){
			this.sysDelFlag = sysDelFlag;
		}else{
			this.sysDelFlag = DataDict.SYS_DEL_FLAG_ENABLE;
		}
		
	}
	public String getSysCreateUser() {
		return sysCreateUser;
	}
	public void setSysCreateUser(String sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}
	public Timestamp getSysCreateDateTime() {
		return sysCreateDateTime;
	}
	public void setSysCreateDateTime(Timestamp sysCreateDateTime) {
		this.sysCreateDateTime = sysCreateDateTime;
	}
	public String getSysUpdateUser() {
		return sysUpdateUser;
	}
	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}
	public Timestamp getSysUpdateDateTime() {
		return sysUpdateDateTime;
	}
	public void setSysUpdateDateTime(Timestamp sysUpdateDateTime) {
		this.sysUpdateDateTime = sysUpdateDateTime;
	}
	public Integer getSysVersion() {
		return sysVersion;
	}
	public void setSysVersion(Integer sysVersion) {
		if(sysDelFlag!=null){
			this.sysVersion = sysVersion;
		}else{
			this.sysVersion = DataDict.SYS_DEL_FLAG_ENABLE;
		}
	}
	public Integer getSysSource() {
		return sysSource;
	}
	public void setSysSource(Integer sysSource) {
		if(sysDelFlag!=null){
			this.sysSource = sysSource;
		}else{
			this.sysSource = DataDict.SYS_DEL_FLAG_ENABLE;
		}
	}
	public Integer getSysArchive() {
		return sysArchive;
	}
	public void setSysArchive(Integer sysArchive) {
		if(sysDelFlag!=null){
			this.sysArchive = sysArchive;
		}else{
			this.sysArchive = DataDict.SYS_DEL_FLAG_ENABLE;
		}
	}
	//--------------
	public EntityModel(){
		super();
	}
	public EntityModel(Integer sysDelFlag, String sysCreateUser, Timestamp sysCreateDateTime, 
			String sysUpdateUser, Timestamp sysUpdateDateTime, Integer sysVersion, 
			Integer sysSource, Integer sysArchive){
		//common start
		this.sysDelFlag = sysDelFlag;
		this.sysCreateUser = sysCreateUser;
		this.sysCreateDateTime = sysCreateDateTime;
		this.sysUpdateUser = sysUpdateUser;
		this.sysUpdateDateTime = sysUpdateDateTime;
		this.sysVersion = sysVersion;
		this.sysSource = sysSource;
		this.sysArchive = sysArchive;
		//common end
	}
}
