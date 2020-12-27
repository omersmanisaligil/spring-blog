package com.React_Spring.SpringBlog.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.React_Spring.SpringBlog.models.Permission;
import com.React_Spring.SpringBlog.models.Role;
import com.React_Spring.SpringBlog.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;
		
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		Set<Role> userRoles=user.getUserRoles();
		
		for(Role r:userRoles) {
			Set<Permission> rolePermissions=r.getPermissions();
			
			for(Permission p:rolePermissions) {
				authorities.add(new SimpleGrantedAuthority(p.getName().toString()));
			}
		}
 		
//		 = user.getUserRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
//				.collect(Collectors.toList());

		return new UserDetailsImpl(
				Long.valueOf(user.getId()), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
