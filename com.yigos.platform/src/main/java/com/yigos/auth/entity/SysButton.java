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
@Table(name="sys_button")
public class SysButton extends EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "module", length = 50)
    private String module;
	
	//event:addEvent();
    @Column(name = "eventMethod", length = 500)
    private String eventMethod;
    
    //class:selectOne selectMulti
    @Column(name = "cls", length = 100)
    private String cls;
    
    //icon:icon-add
    @Column(name = "icon", length = 100)
    private String icon;
    
    @Column(name = "link", length = 200)
    private String link;
    
    @Column(name="remark", length=3000)
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getEventMethod() {
		return eventMethod;
	}

	public void setEventMethod(String eventMethod) {
		this.eventMethod = eventMethod;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
