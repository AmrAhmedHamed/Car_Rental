package com.example.project905;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Project905Application {

		public static void main(String[] args) {
			SpringApplication.run(Project905Application.class, args);
		}



}
