package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class HelloWorld {

	@RequestMapping("/")
	String hello() {
        return "Hello World!";
    }
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(HelloWorld.class, args);
	}
}
