package com.learning.restful_web_services.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.learning.restful_web_services.entities.User;

@Service
public class UserService {

	static long id = 0;
	static List<User> users;

	static {
		users = new ArrayList<>();
		users.add(new User(++id, "chandra", LocalDate.now().minusYears(23)));
		users.add(new User(++id, "venkata", LocalDate.now().minusYears(20)));
	}

	public List<User> fetchUsers() {
		return users;
	}

	public User findUserById(Long id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}

	public void deleteUserById(Long id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}

	public User saveUser(User user) {
		user.setId(++id);
		users.add(user);
		return user;
	}

}
