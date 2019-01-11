
package com.example.demo.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 一个定时任务 继承一个 Job 重写 execute 方法实现
 * @author Administrator
 *
 */
public class DemoJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(new Date().getTime());
	}
	
}
