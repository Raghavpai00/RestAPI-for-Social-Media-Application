package com.Raghav.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Rest Api
//ctrl+shft+o for the organize input

@RestController
public class HelloWorldController {
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource=messageSource;
	}
	
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
	

	@GetMapping(path="/hello-world-internationalized")
public String helloWorldInternationalized(){
		@Nullable
	Locale locale=LocaleContextHolder.getLocale();
		//return messageSource.getMessage("good.morning.message", null,"Default Message" , locale);
		return messageSource.getMessage("good.morning.message", null,"Default Message" , locale);

	//return "Hello World V2";
			
	
	// 'en' - English(good morning)
	// 'nl' - Dutch(goedmorgen)
	// 'fr' - French(Bonjour)
	// 'de' - Deutsch(guten morgen)
}
	
}









