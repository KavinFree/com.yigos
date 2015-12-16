package com.yigos.quartz.service;

import java.util.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.yigos.quartz.entity.SysTaskJob;

/**
 * 定时任务管理类
 */
@Service
public class QuartzServiceImpl {
	
	private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	@Autowired
	private TaskServiceImpl taskService;
	
	//@Scheduled(cron="0 0/1 * * * ?")
	public void initTask() throws Exception {
		List<SysTaskJob> jobList = taskService.findTasks();
		List<SysTaskJob> allJobs =  allJobs();
		Map<String, SysTaskJob> allJobsMap = new HashMap<String, SysTaskJob>();
		if(allJobs!=null && allJobs.size()>0){
			for (SysTaskJob job : allJobs) {
				allJobsMap.put(job.getName(), job);
			}
		}

		for(SysTaskJob job:jobList){
			if(allJobsMap.containsKey(job.getName())){
				this.modifyJobTime(job);
			}else{
				this.addJob(job);
			}
		}
	}
	
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param job
	 */
	public void addJob(SysTaskJob job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobKey jobKey = new JobKey(job.getName());
			JobDetail jobDetail = JobBuilder.newJob(job.getClazz()).withIdentity(jobKey)
					.withIdentity(job.getName(), job.getGroupId())
					.withDescription(job.getRemark()).build();
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getName());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
			// 按表达式构建一个trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroupId())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 1、更新之后，任务将立即按新的时间表达式执行；
	 * 2、可以通过startAt(new Date())设置表达式生效的时间
	 * @param job
	 */
	public void modifyJobTime(SysTaskJob job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroupId());
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder()
					.withIdentity(triggerKey).withSchedule(scheduleBuilder)
					.startAt(new Date())//startTime是表达式生效的时间而非执行时间
					.build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停任务
	 * 
	 * @param job
	 */
	public void pauseJob(SysTaskJob job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroupId());
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复任务
	 * 
	 * @param job
	 */
	public void resumeJob(SysTaskJob job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroupId());
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param job
	 */
	public void removeJob(SysTaskJob job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroupId());
			scheduler.deleteJob(jobKey);// 删除任务
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 立即运行任务
	 * 这里的立即运行，只会运行一次，方便测试时用。
	 * quartz是通过临时生成一个trigger的方式来实现的，
	 * 这个trigger将在本次任务运行完成之后自动删除。
	 * trigger的key是随机生成的
	 * @param job
	 */
	public void startJob(SysTaskJob job){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
		    JobKey jobKey =JobKey.jobKey(job.getName(), job.getGroupId());
		    scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计划中的任务
	 * 指那些已经添加到quartz调度器的任务，因为quartz并没有直接提供这样的查询接口，所以我们需要结合JobKey和Trigger来实现
	 */
	public List<SysTaskJob> allJobs() {
		List<SysTaskJob> jobList = null;
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			jobList = new ArrayList<SysTaskJob>();
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					SysTaskJob job = new SysTaskJob();
					job.setName(jobKey.getName());
					job.setGroupId(jobKey.getGroup());
					job.setRemark("触发器:" + trigger.getKey());
					Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
					job.setStatus(state.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						job.setCron(cronExpression);
					}
					jobList.add(job);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobList;
	}
	
	/**
	 * 运行中的任务
	 */
	public List<SysTaskJob> runningJobs() {
		List<SysTaskJob> jobList = null;
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			jobList = new ArrayList<SysTaskJob>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				SysTaskJob job = new SysTaskJob();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				job.setName(jobKey.getName());
				job.setGroupId(jobKey.getGroup());
				job.setRemark("触发器:" + trigger.getKey());
				Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
				job.setStatus(state.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCron(cronExpression);
				}
				jobList.add(job);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobList;
	}
	
	/**
	 * 启动所有定时任务
	 * 
	 */
	public void startAllJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭所有定时任务
	 * 
	 */
	public void shutdownAllJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}