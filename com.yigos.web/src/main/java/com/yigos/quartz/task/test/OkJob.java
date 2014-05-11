package com.yigos.quartz.task.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class OkJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context){
		System.out.println("---------------hello----------------");
	}

}
