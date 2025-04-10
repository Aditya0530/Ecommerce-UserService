package com.ecommerce.main.servicei;

import java.util.List;
import java.util.Optional;
import java.util.List;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.Order;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

public interface UserService {
	public UserDto saveUser(User user);

	public Iterable<User> loginUser(String username, String password);

	public User getUser(int userId);

	public Iterable<Product> getAll();

	public Iterable<Product> getByName(String productName);

	public void addToCart(int userId, String productName);

	//public String purchaseProduct(int userId, String productName, int quantityAvailable);
	public String purchaseProduct(int userId,int productId,Order order);
	
	
	public Optional<Order> getByUserIdAndProductId(int userId,int productId);
}
