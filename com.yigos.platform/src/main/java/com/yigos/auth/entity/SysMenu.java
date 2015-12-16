package com.yigos.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_menu")
public class SysMenu extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="pid", nullable=false, length=50)
	private String pid;
	
	@Column(name="text", nullable=true, length=200)
	private String text;
	
	@Column(name="iconCls", nullable=true, length=200)
	private String iconCls;
	
	@Column(name="link", nullable=true, length=200)
	private String link;
	
	@Column(name="orderFlag", nullable=false, length=11)
	private Integer orderFlag=1;
	
	@Column(name="remark", length=3000)
	private String remark;
	
	@Transient
	private boolean leaf=false;
	@Transient
	private boolean expandable=true;
	@Transient
	private boolean expanded=false;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public boolean isExpandable() {
		return expandable;
	}
	
	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
	
	public boolean isExpanded() {
		return expanded;
	}
	
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
}
