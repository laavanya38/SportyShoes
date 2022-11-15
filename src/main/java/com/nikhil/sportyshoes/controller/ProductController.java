package com.nikhil.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikhil.sportyshoes.model.Product;
import com.nikhil.sportyshoes.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController 
{
	@Autowired
	private ProductService service;
	
	@GetMapping
	public String addProductPage(Model model)
	{
		model.addAttribute("product", new Product());
		return "addproduct";
	}
	
	@PostMapping
	public String addProduct(@ModelAttribute("product") Product product)
	{
		service.addProduct(product);
		return "redirect:/product?success";
	}
	
	@GetMapping("/{prodId}")
	public String editProductById(@PathVariable Long prodId, Model model)
	{
		Product product = service.getProductById(prodId);
		model.addAttribute("prod", product);
		return "editproduct";
	}
	
	@GetMapping("/delete/{prodId}")
	public String deleteProduct(@PathVariable("prodId") Long prodId, Model model) {
		service.deleteProductById(prodId);
		return "redirect:/admindashboard?success";
	}
}
