package com.React_Spring.SpringBlog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.React_Spring.SpringBlog.models.EPermission;
import com.React_Spring.SpringBlog.models.ERole;
import com.React_Spring.SpringBlog.security.UserDetailsServiceImpl;
import com.React_Spring.SpringBlog.security.jwt.AuthEntryPointJwt;
import com.React_Spring.SpringBlog.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled=true) //preAuthorize, postAuthorize vs vs

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
    public AuthTokenFilter jwtAuthenticationFilter() {
        return new AuthTokenFilter();
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests().antMatchers("/","/users/signup","/users/login").permitAll()
			.antMatchers(HttpMethod.GET,"/blogs/*","/posts/*","/tags/*").permitAll()
			.antMatchers(HttpMethod.GET,"/users/*").permitAll()
			.antMatchers(HttpMethod.DELETE,"/blogs/*","/posts/*","/tags/*").hasAuthority(EPermission.AUTHOR_WRITE.toString())
			.antMatchers(HttpMethod.POST,"/blogs/*","/posts/*","/tags/*").hasAuthority(EPermission.AUTHOR_WRITE.toString())//hasRole(ERole.ROLE_AUTHOR.getRoleName())
			.antMatchers(HttpMethod.PUT,"/blogs/*","/posts/*","/tags/*").hasAuthority(EPermission.AUTHOR_WRITE.toString())
			.antMatchers(HttpMethod.DELETE,"/users/*").hasAuthority(EPermission.USER_WRITE.toString())
			.antMatchers(HttpMethod.POST,"/users/*").hasAuthority(EPermission.USER_WRITE.toString())
			.antMatchers(HttpMethod.PUT,"/users/*").hasAuthority(EPermission.USER_WRITE.toString())
			.anyRequest().authenticated();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
