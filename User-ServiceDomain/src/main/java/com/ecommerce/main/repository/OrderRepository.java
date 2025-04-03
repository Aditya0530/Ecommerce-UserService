package com.ecommerce.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.main.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

}
