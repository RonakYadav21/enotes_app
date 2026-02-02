package com.enotes.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.enotes.Repository.userRepository;
import com.enotes.model.User;
import com.enotes.service.userService;


@Service
public class userServiceImpl implements userService {

	@Autowired
	userRepository userrepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User getUserByEmail(String email) {
		return  userrepository.findByEmail(email);
	}
	@Override
	public Boolean ExistEmail(String email) {
		 User userexist=userrepository.findByEmail(email);
		 return userexist != null;	
		 
	}
	@Override
	public User saveuser(User user) {
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		   User newuser= userrepository.save(user);
		        return newuser ;
		    }
	
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		  session.removeAttribute("successMsg");
		  session.removeAttribute("errormessage");

	}
	@Override
	public User getUser(User user) {
     User getuser=userrepository.findByEmail(user.getEmail());
      return getuser;
	}
	
	
}
