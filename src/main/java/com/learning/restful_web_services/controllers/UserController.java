package com.learning.restful_web_services.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.restful_web_services.entities.User;
import com.learning.restful_web_services.exception.UserNotFoundException;
import com.learning.restful_web_services.service.UserService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/users")
	public List<User> fetchUsers() {
		return userService.fetchUsers();
	}

	@PostMapping("/users")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {

		User savedUser = userService.saveUser(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

//	@GetMapping("/users/{id}")
//	public User getUser(@PathVariable String id) throws UserNotFoundException {
//		User user = userService.findUserById(Long.parseLong(id));
//		if (user == null) {
//			throw new UserNotFoundException("user data not found");
//		}
//		return user;
//	}

	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable String id) throws UserNotFoundException {
		User user = userService.findUserById(Long.parseLong(id));
		if (user == null) {
			throw new UserNotFoundException("user data not found");
		}

		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).fetchUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable String id) throws UserNotFoundException {
		userService.deleteUserById(Long.parseLong(id));
	}

	@GetMapping("/hello/{name}")
	public String welcome(@PathVariable String name) throws UserNotFoundException {
		return "Hello " + name;
	}

	@GetMapping("/hello-world-internationalization")
	public String goodmorningMsg() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "default messsage", locale);
	}

}
