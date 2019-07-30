package com.cignex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cignex.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);

}
