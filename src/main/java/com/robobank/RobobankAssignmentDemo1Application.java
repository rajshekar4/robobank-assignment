package com.robobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.*")
@SpringBootApplication
public class RobobankAssignmentDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(RobobankAssignmentDemo1Application.class, args);
	}

}
