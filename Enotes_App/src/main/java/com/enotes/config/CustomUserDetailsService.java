package com.enotes.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enotes.Repository.userRepository;
import com.enotes.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private userRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	   User user=	userrepo.findByEmail(email);
	   if(user==null) {
		   throw new UsernameNotFoundException("user not found");
	   }else {
		   return new CustomUser(user);
	   }
	}

}
