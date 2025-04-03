package com.ecommerce.main.serviceimpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.io.ObjectInputFilter.Status;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.eclipse.angus.mail.handlers.image_gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.enums.StatusOrder;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.main.model.Address;
import com.ecommerce.main.model.Order;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.OrderRepository;
import com.ecommerce.main.repository.ProductRepository;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public UserDto saveUser(User user) {
		userRepository.save(user);
		return new UserDto(user);
	}

	@Override
	public Iterable<User> loginUser(String username, String password) {

		if ("Admin".equalsIgnoreCase(username) && "Admin".equalsIgnoreCase(password)) {

			return userRepository.findAll();
		} else {
			List<User> user = (List<User>) userRepository.findAllByUsernameAndPassword(username, password);

			if (user.isEmpty()) {
				throw new InvalidCredentialsException("Username And Password Incorrect");
			} else {
				return user;
			}
		}
	}

	@Override
	public Iterable<Product> getAll() {
		String url = "http://localhost:9292/product/getAll";
		Iterable<Product> res = restTemplate.getForObject(url, Iterable.class);
		return res;
	}

	@Override
	public List<Product> getByName(String productName) {
	    String url = "http://localhost:9292/product/getByName/" + productName;
	    List<Product> pro=restTemplate.getForObject(url,List.class);
	    return pro;
	}

	@Override
	public String purchaseProduct(int userId, String productName, int quantityAvailable) {
		User user=userRepository.findByUserId(userId);
		if(user==null) {
			throw new ResourceNotFoundException("No User Found With The Particular Id"+userId);
		}
			 String url = "http://localhost:9292/product/getByName/" + productName;
			 List<Product> pro=restTemplate.getForObject(url, List.class);
			 Order order=new Order();
			//if(pro.getQuantityAvailable() >= quantityAvailable) {
			 
			 for(Product prod:pro) {
				 if(prod.getQuantityAvailable() >= quantityAvailable) {
					
				 }
			 }
				 order.setOrderStatus(StatusOrder.CONFIRMED);
				 double totalAmount = 0.0;
				//double orderTotal=pro.getPrice()*quantityAvailable;
				 
				//totalAmount+=orderTotal;
				 
				 order.setTotalAmount(totalAmount);
				 order.setOrderDate(new Date(System.currentTimeMillis()));
				// order.getProduct().add(pro);
				user.getOrder().add(order);
				userRepository.save(user);
				
				
			     Address addrs=new Address();
				 addrs.setAreaName("PUNE");
				 addrs.setAreaName("Hinjewadi");
				 addrs.setCountryName("India");
				 addrs.setPinCode(422010);
				 order.setProduct(pro);
				 order.setDeliverycharges(totalAmount+10);
				 order.setContactNo(1457123698);
				 
				//userRepository.save(user);
				 orderRepository.save(order);
		     //productRepository.save(pro);
				 // } else { order.setOrderStatus(StatusOrder.CANCELLED); }
				 			 return "Product Purchase Successfully";
	}
	}


