package com.learning.restful_web_services.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.restful_web_services.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
