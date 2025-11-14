package com.Raghav.rest.webservices.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Rest Api
//ctrl+shft+o for the organize input

@RestController
public class HelloWorldController {
	
	@GetMapping(path="/hello-world")
public String helloWorld()
	{
	return "Hello World";
			
}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello World bean");
	}
	
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("hello World,  %s",name));
	}
	
	
	@RequestMapping(method=RequestMethod.GET,path="/hello-Raghav")
	public String helloRaghav() {
		return "Hello Raghav";
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/hello-tridha")
	public String helloTridha() {
		return "hello tridha,how are you";
	}
	
}









