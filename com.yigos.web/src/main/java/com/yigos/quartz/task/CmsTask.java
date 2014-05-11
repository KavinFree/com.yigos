package com.yigos.quartz.task;

import java.io.Serializable;
import com.yigos.framework.model.EntityModel;

public class CmsTask extends EntityModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String taskCode;
	private String jobClass;
	private boolean enable;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}