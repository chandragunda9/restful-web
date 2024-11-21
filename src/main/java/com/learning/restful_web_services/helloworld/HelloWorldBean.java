package com.learning.restful_web_services.helloworld;

public class HelloWorldBean {

	String message;

	public HelloWorldBean(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
