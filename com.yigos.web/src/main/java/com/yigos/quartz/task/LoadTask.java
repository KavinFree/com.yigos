package com.yigos.quartz.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yigos.framework.dao.itf.BaseDao;

public class LoadTask {
	/**
	 * 系统初始加载任务
	 */
	public void loadTask() {
		CmsTask cmsTask = new CmsTask();
		cmsTask.setId("111");
		cmsTask.setTaskCode("aaa11");
		cmsTask.setJobClass("com.yigos.quartz.task.test.TestJob");
		cmsTask.setEnable(true);
		List<CmsTask> tasks = new ArrayList<CmsTask>();
		tasks.add(cmsTask);
		if (tasks.size() > 0) {
			for (int i = 0; i < tasks.size(); i++) {
				CmsTask task = tasks.get(i);
				// 任务开启状态 执行任务调度
				if (task.isEnable()) {
					try {
						JobDetail jobDetail = new JobDetail();
						// 设置任务名称
						if (StringUtils.isNotBlank(task.getTaskCode())) {
							jobDetail.setName(task.getTaskCode());
						} else {
							UUID uuid = UUID.randomUUID();
							jobDetail.setName(uuid.toString());
							task.setTaskCode(uuid.toString());
							// dao.update(task, task.getAttr());
						}
						jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
						// 设置任务执行类
						jobDetail.setJobClass(TaskUtil.getClassByTask(task.getJobClass()));
						// 添加任务参数
						//jobDetail.setJobDataMap(getJobDataMap(task.getAttr()));
						
						// 调度任务
						//方法1
						/* Create a trigger that fires every 10 seconds, forever       
				        Trigger trigger = TriggerUtils.makeSecondlyTrigger(10);//每10秒触发一次
				        trigger.setName("scanTrigger");       
				        // Start the trigger firing from now       
				        trigger.setStartTime(new Date());//设置第一次触发时间            
				        // Associate the trigger with the job in the scheduler   
				        scheduler.scheduleJob(jobDetail, trigger);
				        */
						
						//方法2
						CronTrigger cronTrigger = new CronTrigger("cron_" + i,
								Scheduler.DEFAULT_GROUP, jobDetail.getName(),
								Scheduler.DEFAULT_GROUP);
				        cronTrigger.setCronExpression("0/10 * * * * ?");
						scheduler.scheduleJob(jobDetail, cronTrigger);
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SchedulerException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Autowired
	private BaseDao dao;

	@Autowired
	private Scheduler scheduler;
}
