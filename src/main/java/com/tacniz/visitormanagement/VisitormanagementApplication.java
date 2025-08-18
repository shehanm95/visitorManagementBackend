package com.tacniz.visitormanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VisitormanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitormanagementApplication.class, args);
	}

}
