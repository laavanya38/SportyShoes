package com.nikhil.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhil.sportyshoes.model.Product;
import com.nikhil.sportyshoes.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public void addProduct(Product product)
	{
		repository.save(product);
	}
	
	public List<Product> getAllProducts()
	{
		return repository.findAll();
	}
	
	public Product getProductById(Long id)
	{
		return repository.getById(id);
	}
	
	public void deleteProductById(Long id)
	{
		repository.deleteById(id);
	}
}
