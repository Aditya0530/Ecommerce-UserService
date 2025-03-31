package com.ecommerce.main.dto;

import java.util.List;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.UOrder;
import com.ecommerce.main.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private int userId;
	private String name;  
	private String email;
	private String password;
	private String username;
	private String address;
	private List<Product> product;
	private List<UOrder> order;
	
	public UserDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword(); 
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.product = user.getProduct();
        this.order = user.getOrder();
    }
}
