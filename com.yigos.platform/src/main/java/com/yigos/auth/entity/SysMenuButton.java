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
@Table(name="sys_menu_button")
public class SysMenuButton extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="idMenu", length=50)
	private String idMenu;
	
	@Column(name="idButton", length=50)
	private String idButton;
	
	@Column(name="remark", length=3000)
	private String remark;

}
