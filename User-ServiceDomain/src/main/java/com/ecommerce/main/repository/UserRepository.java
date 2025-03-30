package com.ecommerce.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.main.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
