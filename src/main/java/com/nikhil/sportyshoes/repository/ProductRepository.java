package com.nikhil.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nikhil.sportyshoes.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
