package com.einfochips.webportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableAutoConfiguration
//@PropertySource("file:${WEBPORTAL_PROPERTIES}")
@EnableAsync
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "com.einfochips.webportal" })
public class SpringBootWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

}
