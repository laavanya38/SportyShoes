package com.nikhil.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikhil.sportyshoes.model.User;
import com.nikhil.sportyshoes.service.UserService;

@Controller
@RequestMapping("/adminregistration")
public class AdminRegistrationController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public String adminRegistrationPage(Model model)
	{
		model.addAttribute("admin", new User());
		return "adminregistration";
	}
	
	@PostMapping
	public String registerAdminAccount(@ModelAttribute("admin") User user)
	{
		user.setRoles("ADMIN");
		service.registerUser(user);
		return "redirect:/adminregistration?success";
	}
}
