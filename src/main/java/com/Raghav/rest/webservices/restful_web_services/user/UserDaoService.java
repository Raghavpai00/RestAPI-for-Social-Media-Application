package com.Raghav.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;


@Component
public class UserDaoService {

	private static List<User> users=new ArrayList<>();
	private static int userCount=0;
	
	static {
		users.add(new User(++userCount,"Ram",LocalDate.now().minusYears(30)));
		users.add(new User(++userCount,"sham",LocalDate.now().minusYears(25)));
		users.add(new User(++userCount,"tom",LocalDate.now().minusYears(20)));

	
	}
	
	
	 
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
	 
	Predicate<? super User> predicate=user->user.getId().equals(id);
	return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteById(int id) {
		 
		Predicate<? super User> predicate=user->user.getId().equals(id);
		users.removeIf(predicate);
		}
}


//nikhil sir,onkar p bajaj amc devops engg ,fresher
