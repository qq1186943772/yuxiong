package com.example.demo.quartz;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.example.demo.quartz.task.DemoTask;

@Configuration
public class QuartzConfig {

	@Bean(name="jobDetail")
	public MethodInvokingJobDetailFactoryBean detailFactoryBean(DemoTask task) {
		MethodInvokingJobDetailFactoryBean detail = new MethodInvokingJobDetailFactoryBean();
		detail.setConcurrent(true);
		detail.setName("DemoJob");
		detail.setGroup("DemoGroup");
		detail.setTargetObject(task);
		detail.setTargetMethod("demoMethod");
		return detail;
	}
	
	@Bean(name="jobTrigger")
	public CronTriggerFactoryBean triggerFactoryBean(MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
		trigger.setJobDetail(jobDetail.getObject());
		trigger.setCronExpression("0/10 * * * * ?");
		trigger.setName("DemoTrigger");
		return trigger;
	}
	
	@Bean(name="scheduler")
	public SchedulerFactoryBean schedulerFactory(Trigger jobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(0);
		/*bean.setTriggers(jobTrigger);*/
		return bean;
	}
	
}
