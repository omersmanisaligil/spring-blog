package com.React_Spring.SpringBlog.services;

import com.React_Spring.SpringBlog.dao.RoleDAO;
import com.React_Spring.SpringBlog.dao.UserDAO;
import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.models.Role;
import com.React_Spring.SpringBlog.models.User;
import com.React_Spring.SpringBlog.payload.request.LoginRequest;
import com.React_Spring.SpringBlog.payload.request.SignUpRequest;
import com.React_Spring.SpringBlog.payload.response.JwtResponse;
import com.React_Spring.SpringBlog.payload.response.MessageResponse;
import com.React_Spring.SpringBlog.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
	Authentication authentication = authenticationManager.authenticate(
	new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt = jwtUtils.generateJwtToken(authentication);

	User userDetails = (User) authentication.getPrincipal();
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
}
