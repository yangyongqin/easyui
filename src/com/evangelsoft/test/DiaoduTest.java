package com.evangelsoft.test;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class DiaoduTest implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(new Date() + "XXX×îË§£¡£¡£¡");

	}

	public static void main(String[] args) {
		JobDetail detail = new JobDetailImpl("job1", "group1", DiaoduTest.class);
		CronTriggerImpl cronTrigger = new CronTriggerImpl("job1", "group1");
		try {
			CronExpression cronExpression = new CronExpression("0/1 * * * * ?");
			cronTrigger.setCronExpression(cronExpression);
			SchedulerFactory factory = new StdSchedulerFactory();
			Scheduler scheduler;
			try {
				scheduler = factory.getScheduler();
				try {
					scheduler.scheduleJob(detail, cronTrigger);
					scheduler.start();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}