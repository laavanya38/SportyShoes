package com.nikhil.sportyshoes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nikhil.sportyshoes.model.Cart;
import com.nikhil.sportyshoes.model.Product;
import com.nikhil.sportyshoes.model.Purchase;
import com.nikhil.sportyshoes.service.MyUserDetailsService;
import com.nikhil.sportyshoes.service.PurchaseService;

@Controller
public class PurchaseController {
	
	@Autowired
	private MyUserDetailsService detailsService;
	
	@Autowired
	private PurchaseService service;
		
	@GetMapping("/checkout")
	public String checkout(Model model)
	{
		String productName = Cart.cart.stream().map(Product::getProductName)
                							   .collect(Collectors.joining(", "));;
		String purchaseDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		double totalCost = Cart.cart.stream().mapToDouble(Product::getPrice).sum();
		String user = detailsService.getLoggedInUsername();
		Purchase purchase = new Purchase(productName, purchaseDate, totalCost , user);
		service.addPurchaseDetails(purchase);
		model.addAttribute("cart", Cart.cart);
		model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
	
	@GetMapping("/purchasereport")
	public String getPurchaseReport(Model model)
	{
		List<Purchase> purchases = service.getAllPurchaseReport();
		model.addAttribute("purchases", purchases);
		return "purchasereport";
	}
	
}
