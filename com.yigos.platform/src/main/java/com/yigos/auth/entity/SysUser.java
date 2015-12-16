package com.yigos.auth.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_user")
@NamedQueries({
	@NamedQuery(name="auth.SysUser.all", 
		query="select user from SysUser user where 1=1 "),
	@NamedQuery(name="auth.SysUser.count", 
		query="select count(user.id) from SysUser user where 1=1 ")
})
public class SysUser extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="code", nullable=false, length=50, unique=true)
	@NotBlank
	private String code;
	
	@Column(name="name", nullable=true, length=500)
	private String name;
	
	@Column(name="email", nullable=true, length=200)
	@Email
	private String email;
	
	@Column(name="password", nullable=false, length=100)
	@NotBlank
	@JsonIgnore
	private String password;
	
	@Column(name="phone", length=20)
	private String phone;
	
	@Column(name="address", length=200)
	private String address;
	
	@Column(name="remark", length=3000)
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	//--------------
	public SysUser(){
		super();
	}
	
	public SysUser(String id, String code, String name, String password, 
			String phone, String email, String address, String remark, 
			Integer sysDelFlag, String sysCreateUser, Timestamp sysCreateDateTime, 
			String sysUpdateUser, Timestamp sysUpdateDateTime, Integer sysVersion, 
			Integer sysSource, Integer sysArchive){
		super(sysDelFlag, sysCreateUser, sysCreateDateTime, 
			sysUpdateUser, sysUpdateDateTime, sysVersion, 
			sysSource, sysArchive);
		this.id = id;
		this.code = code;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.remark = remark;
	}
}
