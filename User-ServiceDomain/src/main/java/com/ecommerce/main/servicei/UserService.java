package com.ecommerce.main.servicei;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

public interface UserService {

	public UserDto saveUser(User user);

	public Iterable<User> loginUser(String username, String password);

	public User getUser(int userId);

	public Iterable<Product> getAll();


	public Iterable<Product> getByName(String productName);


	public void addToCart(int userId, int productId);

	public void removeToCart(int userId, int productId);


}
