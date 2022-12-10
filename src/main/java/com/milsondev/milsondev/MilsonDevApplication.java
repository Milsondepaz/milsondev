package com.milsondev.milsondev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;


@SpringBootApplication
public class MilsonDevApplication {
	public static void main(String[] args) {
		SpringApplication.run(MilsonDevApplication.class, args);
	}


	// spring.profiles.active=local



}
