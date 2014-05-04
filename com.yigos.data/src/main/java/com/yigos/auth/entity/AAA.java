package com.yigos.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
//import javax.persistence.SecondaryTables;
//import javax.persistence.SecondaryTable;
//import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.UniqueConstraint;
import javax.persistence.Table;

//@Entity
//@Table(name="sys_aaa")
//@IdClass(IdUK.class)
//@SecondaryTables({  
//    @SecondaryTable(name="Cat1", pkJoinColumns={  
//           @PrimaryKeyJoinColumn(name="cat_id", referencedColumnName="id")}),  
//    @SecondaryTable(name="Cat2", uniqueConstraints={  
//           @UniqueConstraint(columnNames={"storyPart2"})})  
// })
public class AAA implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private String id;
	@Id
	@Column(name="code")
	private String code;
	@Id
	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
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
}
