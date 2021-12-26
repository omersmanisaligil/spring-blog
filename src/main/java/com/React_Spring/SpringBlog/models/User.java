package com.React_Spring.SpringBlog.models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;


@Entity 

@Table(name="USERS")
public class User implements UserDetails  {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="USER_ID")
	private Long id;
	
	@NonNull @Column(name="USERNAME")
	private String username;
	
	@NonNull @Column(name="PASSWORD")
	private String password;
	
	@NonNull @Column(name="EMAIL")
	private String email;
	
	@Column(name="BIO")
	private String bio;

	@OneToMany(mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Blog> blogs=new ArrayList<Blog>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="USER_ROLE",
	joinColumns = {@JoinColumn(name="USER_ID")},
	inverseJoinColumns = {@JoinColumn(name="ROLE_ID")})
	private Set<Role> userRoles=new HashSet<Role>();

	@Column(name="NONEXPIRED")
	private final boolean isAccountNonExpired=true;
	
	@Column(name="NONLOCKED")
	private final boolean isAccountNonLocked=true;
	
	@Column(name="CREDNONEXPIRED")
	private final boolean isCredentialsNonExpired=true;
	
	@NonNull @Column(name="ENABLED")
	private final boolean isEnabled=true;
	
	@NonNull @Transient
	private Collection<? extends GrantedAuthority> grantedAuthorities;

	public User() {
		
	}
    	public User(Long id, String username, String password, String email) {
		this.id = id;
	    	this.username=username;
		this.password=password;
		this.email=email;
    	}
	public User(String username, String password, String email) {
		this.username=username;
		this.password=password;
		this.email=email;
	}

	public User(String username, String password, String email, Set<Role> userRoles) {
		this.username=username;
		this.password=password;
		this.email=email;
		this.userRoles=userRoles;
	}
	
	public User(Long id, String username, String password, String email, String bio, List<Blog> blogs,
			Set<Role> userRoles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.blogs = blogs;
		this.userRoles = userRoles;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return grantedAuthorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities){this.grantedAuthorities=grantedAuthorities;}
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	public Set<Role> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
}
