package com.yigos.auth.entity.config;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.yigos.auth.entity.config.basic.SysConfigTypeBasic;

@Entity
@Table(name="sys_config_type")
public class SysConfigType extends SysConfigTypeBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public SysConfigType() {
	}

}
