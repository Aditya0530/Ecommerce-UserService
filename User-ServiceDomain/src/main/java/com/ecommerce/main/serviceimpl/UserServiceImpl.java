package com.ecommerce.main.serviceimpl;

import java.util.Collections;
import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

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
	public Product getByName(String productName) {
	    String url = "http://localhost:9292/product/getByName/" + productName;
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); //
		 * Set the Accept header for JSON HttpEntity<String> entity = new
		 * HttpEntity<>(headers);
		 * 
		 * // Use the correct response type ResponseEntity<ProductDto> response =
		 * restTemplate.exchange( url, HttpMethod.GET, entity, new
		 * ParameterizedTypeReference<ProductDto>() {} );
		 * 
		 * return response.getBody();
		 */
	    Product pro=restTemplate.getForObject(url, Product.class);
	    return pro;
	}
}

