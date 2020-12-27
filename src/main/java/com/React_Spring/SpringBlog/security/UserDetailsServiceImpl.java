package com.React_Spring.SpringBlog.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserDAO userDAO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
