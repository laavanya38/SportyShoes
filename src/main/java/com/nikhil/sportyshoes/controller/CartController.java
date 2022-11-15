package com.nikhil.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikhil.sportyshoes.model.Cart;
import com.nikhil.sportyshoes.model.Product;
import com.nikhil.sportyshoes.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public String getCartPage(Model model)
	{
		model.addAttribute("cartCount", Cart.cart.size());
		model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", Cart.cart);
		return "cart";
	}
	
	@GetMapping("/{prodId}")
	public String addToCart(@PathVariable Long prodId, Model model)
	{
		Cart.cart.add(service.getProductById(prodId));
		model.addAttribute("cart", Cart.cart);
		model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cartCount", Cart.cart.size());
		return "redirect:/cart";
	}
	
	@GetMapping("/delete/{index}")
	public String removeFromCart(@PathVariable int index, Model model)
	{
		Cart.cart.remove(index);
		model.addAttribute("cart", Cart.cart);
		return "redirect:/cart";
	}
}
