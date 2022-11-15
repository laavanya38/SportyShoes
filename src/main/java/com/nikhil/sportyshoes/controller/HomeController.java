package com.nikhil.sportyshoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.nikhil.sportyshoes.model.Cart;
import com.nikhil.sportyshoes.model.Product;
import com.nikhil.sportyshoes.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("cartCount", Cart.cart.size());
		List<Product> productlist = service.getAllProducts();
		model.addAttribute("prodlist", productlist);
		return "index";
	}
	
	@GetMapping("/login")
	public String loginPage()
	{
		return "login";
	}
	
	@GetMapping("/admindashboard")
	public String admin(Model model)
	{
		List<Product> productlist = service.getAllProducts();
		model.addAttribute("prods", productlist);
		return "admindashboard";
	}
}
