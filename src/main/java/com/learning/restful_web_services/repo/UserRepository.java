package com.learning.restful_web_services.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.restful_web_services.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
