package com.yigos.auth.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IdUK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String code;
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
