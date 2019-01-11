package com.example.demo.quartz;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;;

public class QuartzManager {

	private final static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME"; // 任务分组
	private final static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME"; // 触发器组
	
	/**
	 * 添加定时任务 默认分组
	 * @param scheduler
	 * @param jobName
	 * @param calName
	 * @param time
	 * @throws SchedulerException
	 */
	public static void addJob(Scheduler scheduler,String jobName,Class<Job> calName,String time) throws SchedulerException {
		
		JobDetailImpl detail = new JobDetailImpl();
		detail.setName(jobName);
		detail.setGroup(JOB_GROUP_NAME);
		detail.setJobClass(calName);
		
		TriggerKey key = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
		
		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setKey(key);
		
		scheduler.scheduleJob(detail,trigger);
		
	}
	/**
	 * 添加定时任务，之定义分组
	 * @param scheduler
	 * @param triggerGroup
	 * @param jobName
	 * @param jobGroup
	 * @param calName
	 * @param time
	 * @throws SchedulerException
	 */
	public static void addJob(Scheduler scheduler,String triggerGroup,
			String jobName,String jobGroup,Class<Job> calName,String time) throws SchedulerException {
		
		JobDetailImpl detail = new JobDetailImpl();
		detail.setName(jobName);
		detail.setGroup(jobGroup);
		detail.setJobClass(calName);
		
		TriggerKey key = new TriggerKey(jobName, triggerGroup);
		
		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setKey(key);
		
		scheduler.scheduleJob(detail,trigger);
		
	}
	
	/**
	 * 移除某个默认分组的定时任务
	 * @param scheduler
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(Scheduler scheduler,String jobName,String triggerName) throws SchedulerException {
		TriggerKey key = new TriggerKey(triggerName, TRIGGER_GROUP_NAME);
		scheduler.pauseTrigger(key);
		scheduler.unscheduleJob(key);
		JobKey job = new JobKey(jobName, JOB_GROUP_NAME);
		scheduler.deleteJob(job);
	}
	
	/**
	 * 移除某个 自定义分组的 定时任务
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 * @param triggerName
	 * @param triggerGroup
	 * @throws SchedulerException
	 */
	public static void removeJob(Scheduler scheduler,String jobName,String jobGroup,
			String triggerName,String triggerGroup) throws SchedulerException {
		TriggerKey key = new TriggerKey(triggerName, triggerGroup);
		scheduler.pauseTrigger(key);
		scheduler.unscheduleJob(key);
		JobKey job = new JobKey(jobName, jobGroup);
		scheduler.deleteJob(job);
	}
	
	/**
	 * 修改某个默认分组的定时任务
	 * @param scheduler
	 * @param jobName
	 * @param triggerName
	 * @param time
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public static void modifyJobTime(Scheduler scheduler,String jobName,String triggerName,String time) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName,TRIGGER_GROUP_NAME);
		CronTrigger  trigger =  (CronTrigger) scheduler.getTrigger(triggerKey);
		if(trigger == null) {
			return ;
		}
		String oldTime = trigger.getCronExpression();
		if(!oldTime.equals(time)) {
			JobDetailImpl detail = new JobDetailImpl();
			detail.setName(jobName);
			detail.setGroup(JOB_GROUP_NAME);
			Class<Job> clazz = (Class<Job>) detail.getJobClass();
			removeJob(scheduler, jobName, triggerName);
			addJob(scheduler, jobName, clazz, time);
		}
	}
	
	/**
	 * 修改某个自定义分组的定时任务
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 * @param triggerName
	 * @param triggerGroup
	 * @param time
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public static void modifyJobTime(Scheduler scheduler,String jobName,String jobGroup,
			String triggerName,String triggerGroup,String time) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroup);
		CronTrigger  trigger =  (CronTrigger) scheduler.getTrigger(triggerKey);
		if(trigger == null) {
			return ;
		}
		String oldTime = trigger.getCronExpression();
		if(!oldTime.equals(time)) {
			JobDetailImpl detail = new JobDetailImpl();
			detail.setName(jobName);
			detail.setGroup(jobGroup);
			Class<Job> clazz = (Class<Job>) detail.getJobClass();
			removeJob(scheduler, jobName, triggerName);
			addJob(scheduler, jobName, clazz, time);
		}
	}
	
	/**
	 * 启动所有的定时任务
	 * @param scheduler
	 * @throws SchedulerException
	 */
	public static void startJobs(Scheduler scheduler) throws SchedulerException {
		scheduler.start();
	}
	
	/**
	 * 停止所有的定时任务
	 * @param scheduler
	 * @throws SchedulerException
	 */
	public static void shutdownJobs(Scheduler scheduler) throws SchedulerException {
		scheduler.shutdown();
	}
	
}
