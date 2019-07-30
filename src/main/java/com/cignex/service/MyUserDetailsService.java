package com.cignex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cignex.entity.CustomUserDetails;
import com.cignex.entity.User;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService service;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=service.findByName(username);
		if(user==null)
			throw new UsernameNotFoundException("Username is not found"+ username);
		
		return new CustomUserDetails(user);
	}

}
