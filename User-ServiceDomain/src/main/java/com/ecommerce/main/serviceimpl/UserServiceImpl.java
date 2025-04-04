package com.ecommerce.main.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.exceptionhandler.EmailSendingException;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.EmailService;
import com.ecommerce.main.servicei.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private EmailService emailService;

	private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto saveUser(User user) {
		userRepository.save(user);
		return new UserDto(user);
	}

	@Override
	public Iterable<User> loginUser(String username, String password) {

	 if ("Admin".equalsIgnoreCase(username) && "Admin".equalsIgnoreCase(password)) {
	return userRepository.findAll();
		} 
	 else {
	 List<User> user = (List<User>) userRepository.findAllByUsernameAndPassword(username, password);

	if (user.isEmpty()) {
	throw new InvalidCredentialsException("Username And Password Incorrect");
	} 
	else {
	String userEmail = user.get(0).getEmail();
	try {
	emailService.sendEmail(userEmail, "Login Successful", "You have successfully logged in.");
	} 
	catch (EmailSendingException e) {
    LOGGER.error("Failed to send email", e);
	}
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
		List<Product> pro = restTemplate.getForObject(url, List.class);
		return pro;
	}

	@Override
	public User getUser(int userId) {
	User u = userRepository.findAllByUserId(userId);
	return u;
	}
}
