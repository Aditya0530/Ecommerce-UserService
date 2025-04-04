package com.ecommerce.main.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Integer>{

public Iterable<User> findAllByUsernameAndPassword(String username, String password);
public User findAllByUserId(int id);

}
