package com.yigos.quartz.task;

public class TaskUtil {
	/**
	 * 任务执行类名
	 * 
	 * @param taskClassName
	 *            任务执行类名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getClassByTask(String taskClassName)
			throws ClassNotFoundException {
		Class<?> clazz = Class.forName(taskClassName);
		return clazz;
	}
}
