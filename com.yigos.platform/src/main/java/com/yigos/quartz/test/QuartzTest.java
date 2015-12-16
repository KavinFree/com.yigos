package com.yigos.quartz.test;

import org.quartz.Job;
import org.quartz.simpl.LoadingLoaderClassLoadHelper;
import org.quartz.spi.ClassLoadHelper;
import com.yigos.quartz.service.QuartzServiceImpl;
import com.yigos.quartz.entity.SysTaskJob;

/**
 * 测试类
 */
public class QuartzTest {
	
	public static void main(String[] args) {
		try {
			QuartzServiceImpl quartzServiceImpl = new QuartzServiceImpl();
			SysTaskJob job = new SysTaskJob();
			job.setName("动态任务调度");
			job.setGroupId("JOBGROUP_NAME");
			ClassLoadHelper loadHelper = new LoadingLoaderClassLoadHelper();
			@SuppressWarnings("unchecked")
			Class<? extends Job> clazz = (Class<? extends Job>) loadHelper.loadClass("com.yigos.quartz.job.QuartzJob");
			job.setClazz(clazz);
			job.setCron("0/2 * * * * ?");
			System.out.println("【系统启动】开始(每2秒输出一次)...");
			quartzServiceImpl.addJob(job);
			
			Thread.sleep(4000);
			System.out.println("【修改时间】开始(每4秒输出一次)...");
			job.setCron("*/4 * * * * ?");
			quartzServiceImpl.modifyJobTime(job);
			
			Thread.sleep(16000);
			System.out.println("【移除定时】开始...");
			quartzServiceImpl.removeJob(job);
			System.out.println("【移除定时】成功");

			System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
			job.setCron("*/10 * * * * ?");
			quartzServiceImpl.addJob(job);
			
			Thread.sleep(60000);
			System.out.println("【移除定时】开始...");
			quartzServiceImpl.removeJob(job);
			System.out.println("【移除定时】成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}