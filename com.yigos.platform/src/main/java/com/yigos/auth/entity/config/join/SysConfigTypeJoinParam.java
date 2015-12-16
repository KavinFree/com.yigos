package com.yigos.auth.entity.config.join;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.yigos.auth.entity.config.SysConfigParam;
import com.yigos.auth.entity.config.basic.SysConfigTypeBasic;

@Entity
@Table(name="sys_config_type")
public class SysConfigTypeJoinParam extends SysConfigTypeBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="categoryId", insertable=false, updatable=false)
	private Collection<SysConfigParam> paramItems = new LinkedHashSet<SysConfigParam>();
	
	public SysConfigTypeJoinParam() {
	}

	public Collection<SysConfigParam> getParamItems() {
		return paramItems;
	}

	public void setParamItems(Collection<SysConfigParam> paramItems) {
		this.paramItems = paramItems;
	}
	
}
