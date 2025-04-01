package com.ecommerce.main.servicei;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

public interface UserService {
public UserDto saveUser(User user);

public Iterable<User> loginUser(String username, String password);

public Iterable<Product> getAll();

public Product getByName(String productName);

}
