package com.yigos.quartz.task;

public interface TaskEnum {
	/**
	 * 任务执行周期cron表达式
	 */
	public static int EXECYCLE_CRON=2;
	/**
	 * 任务执行周期自定义
	 */
	public static int EXECYCLE_DEFINE=1;
	/**
	 * 执行周期-分钟
	 */
	public static int EXECYCLE_MINUTE=1;
	/**
	 * 执行周期-小时
	 */
	public static int EXECYCLE_HOUR=2;
	/**
	 * 执行周期-日
	 */
	public static int EXECYCLE_DAY=3;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_WEEK=4;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_MONTH=5;
}
