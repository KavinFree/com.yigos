package com.yigos.quartz.task.test;

import java.text.ParseException;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.yigos.quartz.task.TaskUtil;

public class TestJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("---------------quartz -----test----------------");
		/*try {
			JobDetail exitJob = context.getScheduler().getJobDetail("aaa11", Scheduler.DEFAULT_GROUP);
			
			if(true){
				JobDetail jobDetail = context.getScheduler().getJobDetail("aaa11", Scheduler.DEFAULT_GROUP);
				jobDetail.setName("aaa22");
				jobDetail.setJobClass(TaskUtil.getClassByTask("com.yigos.quartz.task.test.OkJob"));//
				CronTrigger cronTrigger = new CronTrigger("cron_2",
						Scheduler.DEFAULT_GROUP, jobDetail.getName(),
						Scheduler.DEFAULT_GROUP);
		        cronTrigger.setCronExpression("0/15 * * * * ?");
		        context.getScheduler().scheduleJob(jobDetail, cronTrigger);
			}
			
	        JobDetail jobDetail2 = context.getScheduler().getJobDetail("aaa11", Scheduler.DEFAULT_GROUP);
	        CronTrigger cronTrigger2 = new CronTrigger("cron_1",
					Scheduler.DEFAULT_GROUP, jobDetail2.getName(),
					Scheduler.DEFAULT_GROUP);
	        cronTrigger2.setCronExpression("0/3 * * * * ?");
	        context.getScheduler().deleteJob("aaa11", Scheduler.DEFAULT_GROUP);
	        context.getScheduler().scheduleJob(jobDetail2, cronTrigger2);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
	}
}
