package com.ecommerce.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	public Iterable<User> findAllByUsernameAndPassword(String username, String password);

	public User findAllByUserId(int id);

	public void save(List l);

}
