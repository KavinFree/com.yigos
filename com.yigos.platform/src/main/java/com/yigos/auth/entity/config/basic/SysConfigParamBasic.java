package com.yigos.auth.entity.config.basic;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import com.yigos.framework.model.EntityModel;

@MappedSuperclass
public class SysConfigParamBasic extends EntityModel implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="code", nullable=false, length=50, unique=true)
	private String code;
	
	@Column(name="name", length=500)
	private String name;
	
	@Column(name="remark", length=3000)
	private String remark;
	
	@Column(name="categoryId", length=50, nullable=false)
	private String categoryId;
	
	public SysConfigParamBasic() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
