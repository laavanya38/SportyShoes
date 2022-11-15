package com.nikhil.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhil.sportyshoes.model.Purchase;
import com.nikhil.sportyshoes.repository.PurchaseRepository;

@Service
public class PurchaseService {
	
	@Autowired
	PurchaseRepository repository;
	
	public void addPurchaseDetails(Purchase purchase)
	{
		repository.save(purchase);
	}
	
	public List<Purchase> getAllPurchaseReport()
	{
		return repository.findAll();
	}
}
