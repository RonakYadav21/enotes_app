package com.enotes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
private UserDetailsService userDetailsService;
	
@Bean
public  BCryptPasswordEncoder passwordencoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider daoAuthenticationProvider() {
	DaoAuthenticationProvider daoAuthenticationprovider=new DaoAuthenticationProvider();
	daoAuthenticationprovider.setUserDetailsService(userDetailsService);
	daoAuthenticationprovider.setPasswordEncoder(passwordencoder());
	return daoAuthenticationprovider;
}

@Bean
public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
	//http:localhost:8080/register,signin   --can access without security
	//http:localhost:8080/user/addnotes,viewnotes--can access after login
	http.csrf(csrf->csrf.disable()).cors(cors->cors.disable()).authorizeHttpRequests(req->req.requestMatchers("/user/**") //The custom login page is at /signin (instead of the default Spring Security login page)
			.hasRole("USER").requestMatchers("/**").permitAll()).formLogin(form->form.loginPage("/signin").loginProcessingUrl("/userLogin").defaultSuccessUrl("/user/addnotes")           

	)
	.logout(logout->logout.permitAll());
	return http.build();
}
}
