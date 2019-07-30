package com.cignex.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cignex.entity.CustomUserDetails;
import com.cignex.entity.Tes;
import com.cignex.entity.Test;
import com.cignex.entity.Token;
import com.cignex.entity.User;
import com.cignex.jwt.JwtTokenUtil;
import com.cignex.service.MyUserDetailsService;

@RestController
public class TestController {
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil util;
	@GetMapping("/login")
	public CustomUserDetails getUser(@RequestBody User user) {
		return (CustomUserDetails) userDetailsService.loadUserByUsername(user.getName());
	}
	@PostMapping("/token")
	@Cacheable("user")
	public Token generateToken(@RequestBody User user) {
		CustomUserDetails customUserDetails=(CustomUserDetails) userDetailsService.loadUserByUsername(user.getName());
		Token t=new Token();
		t.setToken(util.generateToken(customUserDetails));
		return t;
	}
	
	@GetMapping("/validate")
	public User validate(@RequestParam String token) {
		return util.validate(token);
	}
	
	@GetMapping("/t")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public String test() {
		return "hello";
	}
	
	@PostMapping("/test")
	public Test getAll(@RequestBody Test test) {
		System.out.println(test);
		return test;
	}
	
	@GetMapping("/get")
	public Test t() {
		Tes t=new Tes();
		t.setClas("1");
		t.setGradeBStudent("2");
		t.setGradeAStudent("1");
		t.setTotalStudent("100");
		Tes t1=new Tes();
		t1.setClas("11");
		t1.setGradeAStudent("11");
		t1.setGradeBStudent("12");
		t1.setTotalStudent("1010");
		List<Tes> tt=new ArrayList<Tes>();
		tt.add(t);
		tt.add(t1);
		Test test=new Test();
		test.setSchoolName("s");
		test.setClassDetails(tt);
		System.out.println(test);
		return test;
	}
	
}
