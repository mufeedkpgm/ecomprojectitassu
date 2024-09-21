package com.itassu.ecomproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.itassu.ecomproject")
public class EcomprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomprojectApplication.class, args);
	}

}
