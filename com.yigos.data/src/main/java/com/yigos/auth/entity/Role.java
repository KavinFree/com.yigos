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
@Table(name="sys_role")
public class Role extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=40)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="name", nullable=true, length=20)
	private String name;
	
	@Column(name="remark", nullable=true, length=20)
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
