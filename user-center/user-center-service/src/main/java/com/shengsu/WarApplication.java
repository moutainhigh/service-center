package com.shengsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ImportResource({"classpath:application-context.xml"})
public class WarApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	            return application.sources(WarApplication.class);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WarApplication.class, args);
	}

}
