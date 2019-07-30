package com.cignex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JwtAuthenticationWithRoleBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationWithRoleBaseApplication.class, args);
	}

}
