package com.nikhil.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.nikhil.sportyshoes.model.User;
import com.nikhil.sportyshoes.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/registration")
	public String userRegistrationPage(Model model)
	{
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") User user)
	{
		user.setRoles("USER");
		service.registerUser(user);
		return "redirect:/registration?success";
	}
	
	@GetMapping("/registeredusers")
	public String getRegisteredUsers(Model model)
	{
		List<User> users = service.getAllRegisteredUser();
		model.addAttribute("users", users);
		return "registeredusers";
	}
	
}
