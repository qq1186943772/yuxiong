package com.example.demo.quartz.task;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class DemoTask{

	public void demoMethod() {
		System.out.println(new Date().getTime());
	}
	
}
