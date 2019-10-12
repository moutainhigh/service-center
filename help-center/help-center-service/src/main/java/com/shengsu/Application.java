package com.shengsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ImportResource({"classpath:application-context.xml"})
public class Application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			SpringApplication.run(Application.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
