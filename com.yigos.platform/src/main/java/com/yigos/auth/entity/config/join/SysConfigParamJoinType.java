package com.yigos.auth.entity.config.join;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.yigos.auth.entity.config.SysConfigType;
import com.yigos.auth.entity.config.basic.SysConfigParamBasic;

@Entity
@Table(name="sys_config_param")
public class SysConfigParamJoinType extends SysConfigParamBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="categoryId", nullable=false, insertable=false, updatable=false)
	private SysConfigType sysConfigType;
	
	public SysConfigParamJoinType() {
	}

	public SysConfigType getSysConfigType() {
		return sysConfigType;
	}

	public void setSysConfigType(SysConfigType sysConfigType) {
		this.sysConfigType = sysConfigType;
	}
	
}
