package com.enotes.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.enotes.model.User;
import com.enotes.service.userService;

import jakarta.servlet.http.HttpSession;

@Controller
public class homeController {
	
	@Autowired
	private userService userservice;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	
	@ModelAttribute//It ensures that the User object is added to the Model for every request.

	public void  getUser(Principal p,Model m) {
		if(p!=null) {
			String email=p.getName();
			User userdetail=userservice.getUserByEmail(email);
			m.addAttribute("user", userdetail);
	}
	}
	@GetMapping("/signin")
	public String loginpage() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterPage() {
	    return "register";  // ✅ Ensure this matches your Thymeleaf template name
	}


	@PostMapping("/UserRegister")
	public String saveuser(@ModelAttribute("user") User user ,HttpSession session) {
	    if (user == null) {
	        session.setAttribute("errormessage", "Form data is missing");
	        return "redirect:/register";
	    }

		System.out.println("user"+user.getName());
		
		Boolean Existuser=userservice.ExistEmail(user.getEmail());
		if(Existuser) {
	     session.setAttribute("errormessage", "User Already exist");
		}
		else {
			
			 User saveduser= userservice.saveuser(user);
			 if(saveduser!=null) {
				 session.setAttribute("successMsg", "User saved successfully");
			 }
			 else {
				 session.setAttribute("errormessage", "something went wrong");
			 }
			
		}
		return "redirect:/register";
	}

	
}