package com.yigos.auth.entity.join;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.yigos.auth.entity.SysButton;
import com.yigos.auth.entity.SysMenu;
import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_menu_button")
public class SysMenuButtonJoin extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="remark", length=3000)
	private String remark;

	//bi-directional many-to-one association to SysRole
	@ManyToOne
	@JoinColumn(name="idMenu")
	private SysMenu sysMenu;

	//bi-directional many-to-one association to SysUser
	@ManyToOne
	@JoinColumn(name="idButton")
	private SysButton sysButton;

	public SysMenuButtonJoin() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	public SysButton getSysButton() {
		return sysButton;
	}

	public void setSysButton(SysButton sysButton) {
		this.sysButton = sysButton;
	}
	
}
