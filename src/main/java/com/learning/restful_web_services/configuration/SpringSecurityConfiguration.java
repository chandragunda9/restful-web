package com.learning.restful_web_services.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth
				.requestMatchers("/").permitAll()
				.anyRequest().authenticated()
				);

		http.httpBasic(withDefaults());

		http.csrf().disable();
		
		return http.build();
	}

}
