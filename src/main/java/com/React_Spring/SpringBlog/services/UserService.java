package com.React_Spring.SpringBlog.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.React_Spring.SpringBlog.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.React_Spring.SpringBlog.dao.RoleDAO;
import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.models.Role;
import com.React_Spring.SpringBlog.models.User;
import com.React_Spring.SpringBlog.payload.request.LoginRequest;
import com.React_Spring.SpringBlog.payload.request.SignUpRequest;
import com.React_Spring.SpringBlog.payload.response.JwtResponse;
import com.React_Spring.SpringBlog.payload.response.MessageResponse;
import com.React_Spring.SpringBlog.security.UserDetailsImpl;
import com.React_Spring.SpringBlog.security.jwt.JwtUtils;

@Service
public class UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public UserService(UserDAO userDAO,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDAO=userDAO;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	public List<User> getAllUsers(){
		return (List<User>) userDAO.findAll();
	}
	
	public Optional<User> getOneUserById(int id) {
		return userDAO.findById(id);
	}
	
	public void deleteUser(int id) {		
		userDAO.deleteById(id);
	}
	

	public ResponseEntity<?> updateUser(User user,int id) { 			
		User toUpdate=userDAO.findById(id).get();
		StringBuilder resp=new StringBuilder("");
		
		if(!toUpdate.getUsername().equals(user.getUsername())) {
			resp.append("Sorry, username cannot be changed\n");
			user.setUsername(toUpdate.getUsername());
		}
		
		String newEmail=user.getEmail();
		if(userDAO.existsByEmail(newEmail) && !toUpdate.getEmail().equals(newEmail)) {
			resp.append("This email is already in use\n");
			user.setEmail(toUpdate.getEmail());
		}
		
		toUpdate=user;
		toUpdate.setId(id);
		
		//TODO password'un hashlenmeden gözükmesi işine mantıklı bi çözüm
		toUpdate.setPassword(bCryptPasswordEncoder.encode(toUpdate.getPassword()));
			
		System.out.println(toUpdate.getId());
		userDAO.save(toUpdate);
		return ResponseEntity.ok(new MessageResponse(resp+"User updated"));
		
	}
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
				
		return ResponseEntity.ok(new JwtResponse(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 roles));	
	}
	
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
		if(userDAO.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken."));
		}
		if(userDAO.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: This email is already in use"));
		}
		
		User user = new User(signUpRequest.getUsername(),
							bCryptPasswordEncoder.encode(signUpRequest.getPassword()),
							signUpRequest.getEmail());
		
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleDAO.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			System.err.println("strroles=null");
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleDAO.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					
					break;
				case "author":
					Role authorRole = roleDAO.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(authorRole);

					break;
				default:
					Role userRole = roleDAO.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setUserRoles(roles);
		userDAO.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	public User findUserByEmail(String email) {
		return userDAO.findByEmail(email).get();
	}
	
	public User findUserByUsername(String username) {
		return userDAO.findByUsername(username).get();
	}

	public Page<User> searchByUsername(String username, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return userDAO.searchByUsername(username,pageable);
	}

	public Page<User> searchByRoles(String role, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return roleDAO.findUsersByRole(role,pageable);
	}

	public Page<Blog> getUserBlogs(int id, int page, int size){
		User user = userDAO.findById(id).get();
		List<Blog> blogs = user.getBlogs();

		PagedListHolder<Blog> blogPage = new PagedListHolder(blogs);
		blogPage.setPage(page);
		blogPage.setPageSize(size);

		Page<Blog> userBlogs = new PageImpl<>(blogPage.getPageList());

		return userBlogs;
	}

}
