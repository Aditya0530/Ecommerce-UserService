package com.ecommerce.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.main.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
