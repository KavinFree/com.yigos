package com.yigos.quartz.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;
import org.quartz.Job;

import com.yigos.framework.model.EntityModel;

@Entity
@Table(name="sys_task_job",
	uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class SysTaskJob extends EntityModel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/*
	 * 任务标识
	 */
	@Id
	@Column(name="id", unique = true, nullable=false, length=50)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/*
	 * 任务名称
	 */
	@Column(name="name", nullable=false, length=200)
	@NotBlank
	private String name;

	/*
	 * 任务组
	 */
	@Column(name="groupId", nullable=false, length=100)
	@NotBlank
	private String groupId;

	/*
	 * 任务表达式
	 */
	@Column(name="cron", nullable=false, length=100)
	@NotBlank
	private String cron;

	/*
	 * 任务描述
	 */
	@Column(name="remark", length=3000)
	private String remark;

	/*
	None：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除
	NORMAL:正常状态
	PAUSED：暂停状态
	COMPLETE：触发器完成，但是任务可能还正在执行中
	BLOCKED：线程阻塞状态
	ERROR：出现错误
	 */
	private transient String status;

	/*
	 * 任务内容
	 */
	@Column(name="clazz", nullable=false, length=100)
	private Class <? extends Job> clazz;
	
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Class<? extends Job> getClazz() {
		return clazz;
	}
	public void setClazz(Class<? extends Job> clazz) {
		this.clazz = clazz;
	}
	
}
