package com.learning.restful_web_services.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

import com.learning.restful_web_services.entities.Post;
import com.learning.restful_web_services.entities.User;
import com.learning.restful_web_services.exception.PostNotFoundException;
import com.learning.restful_web_services.exception.UserNotFoundException;
import com.learning.restful_web_services.repo.PostRepository;
import com.learning.restful_web_services.repo.UserRepository;
import com.learning.restful_web_services.service.UserService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserJpaController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/jpa/users")
	public List<User> fetchUsers() {
		return userRepository.findAll();
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {

		User savedUser = userRepository.save(user);

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

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable String id) throws UserNotFoundException {
		Optional<User> optional = userRepository.findById(Long.parseLong(id));
		if (!optional.isPresent()) {
			throw new UserNotFoundException("user data not found");
		}

		EntityModel<User> entityModel = EntityModel.of(optional.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).fetchUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable String id) throws UserNotFoundException {
		userRepository.deleteById(Long.parseLong(id));
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> fetchPostsByUserid(@PathVariable String id) throws UserNotFoundException {
		Optional<User> optional = userRepository.findById(Long.parseLong(id));
		if (!optional.isPresent()) {
			throw new UserNotFoundException("user data not found");
		}
		return optional.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> submitPost(@PathVariable Long id, @Valid @RequestBody Post post)
			throws UserNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			throw new UserNotFoundException("user data not found");
		}

		post.setUser(optional.get());
		Post savedPost = postRepository.save(post);

		URI link = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(link).build();
	}

	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public Post getPost(@PathVariable Long id, @PathVariable Long postId)
			throws UserNotFoundException, PostNotFoundException {
		Optional<User> optional = userRepository.findById(id);
		if (!optional.isPresent()) {
			throw new UserNotFoundException("user data not found");
		}

		Optional<Post> postOptional = postRepository.findById(postId);
		if (postOptional.isEmpty() || !postOptional.get().getUser().getId().equals(id)) {
			throw new PostNotFoundException("post:"+postId+" data not found with the user "+id);
		}
		return postOptional.get();
	}

}
