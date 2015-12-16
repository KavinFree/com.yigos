package com.yigos.quartz.service;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import com.yigos.framework.service.BaseServiceImpl;
import com.yigos.quartz.entity.SysTaskJob;

@Service
public class TaskServiceImpl extends BaseServiceImpl{
	
	public String getCronExpression(String taskId){
		SysTaskJob task = this.dao.find(SysTaskJob.class, taskId);
		return task.getCron();
	}
	
	public List<SysTaskJob> findTasks(){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTaskJob.class);
		@SuppressWarnings("unchecked")
		List<SysTaskJob> list = (List<SysTaskJob>) this.dao.findByCriteria(criteria);
		return list;
	}
	
}
