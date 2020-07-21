package com.example.excelProj;

import com.example.excelProj.Commons.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;
import java.net.InetAddress;

@ComponentScan
@SpringBootApplication
public class ExcelProjApplication extends SpringBootServletInitializer {
//SpringBootServletInitializer
	public static void main(String[] args) {
		SpringApplication.run(ExcelProjApplication.class, args);

		final String dir = System.getProperty("user.dir");
		System.out.println("current dir = " + dir);
		System.out.println("Current path = " + Constants.galleryImagePath);

		Constants.SERVER_PATH = dir;
	}

//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ExcelProjApplication.class);
//	}
}
