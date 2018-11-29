package com.db.test.Testevent;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class BeanEventStart implements ApplicationListener<ContextStartedEvent> {
	long stime,entime;
	public void onApplicationEvent(ContextStartedEvent event) {
		System.out.println("Application Started");
		}
		
		
	}


