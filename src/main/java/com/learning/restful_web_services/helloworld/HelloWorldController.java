package com.learning.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.learning.restful_web_services.exception.UserNotFoundException;

@RestController
public class HelloWorldController {
	
	@GetMapping("/hello-world/{name}")
	public HelloWorldBean welcome(@PathVariable String name) throws UserNotFoundException {
		return new HelloWorldBean("hi "+name);
	}
	
	@GetMapping("/hello-world")
	public String helloWorld() throws UserNotFoundException {
		return "Hello World";
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() throws UserNotFoundException {
		return new HelloWorldBean("hi");
	}

}
