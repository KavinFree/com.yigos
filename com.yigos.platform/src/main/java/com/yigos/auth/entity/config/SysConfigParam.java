package com.yigos.auth.entity.config;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.yigos.auth.entity.config.basic.SysConfigParamBasic;

@Entity
@Table(name="sys_config_param")
public class SysConfigParam extends SysConfigParamBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public SysConfigParam() {
	}

}
