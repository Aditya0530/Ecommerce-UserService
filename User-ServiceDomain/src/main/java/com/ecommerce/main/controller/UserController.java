package com.ecommerce.main.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.enums.StatusOrder;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.model.Order;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.servicei.UserService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/save_User")
	public ResponseEntity<UserDto> saveProduct(@Valid @RequestBody User user) {
		UserDto userDto = userService.saveUser(user);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
	}

	@GetMapping("/login/{username}/{password}")
	public ResponseEntity<Iterable<User>> login(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		Iterable<User> user = userService.loginUser(username, password);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/search_Product")
	public ResponseEntity<Iterable<Product>> getAll() {
		Iterable<Product> p = userService.getAll();
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping("/search_ProductByName/{productName}")
	public ResponseEntity<Iterable<Product>> getproductByName(@PathVariable("productName") String productName) {
		Iterable<Product> p = userService.getByName(productName);
		return new ResponseEntity<Iterable<Product>>(p, HttpStatus.OK);
	}

	@PutMapping("/add/{userId}/{productId}")
	public ResponseEntity<String> updateUserProducts(@PathVariable("userId") int userId,
			@PathVariable("productId") int productId) {
		userService.addToCart(userId, productId);
		return new ResponseEntity<String>("Products updated successfully", HttpStatus.OK);

	}

	@PatchMapping("/place_Order/{userId}/{productId}")
	public ResponseEntity<String> purchaseProduct(@PathVariable("userId") int userId,
			@PathVariable("productId") int productId, @RequestBody Order order) {
		String msg = userService.placeOrder(userId, productId, order);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@PatchMapping("/order_Status/{orderId}/{orderStatus}")
	public ResponseEntity<String> changeStatus(@PathVariable("orderId") int orderId,
			@PathVariable("orderStatus") StatusOrder orderStatus) {
		userService.orderStatus(orderId, orderStatus);
		return new ResponseEntity<>("Order Status Updated", HttpStatus.OK);
	}

	@GetMapping("/view_Cart/{userId}")
	public ResponseEntity<Map<String, Object>> viewCart(@PathVariable("userId") int userId) {
		Map<String, Object> mapProduct = userService.viewCart(userId);
		return new ResponseEntity<>(mapProduct, HttpStatus.OK);
	}

	@DeleteMapping("/remove/{userId}/{productId}")
	public ResponseEntity<String> removeUserProducts(@PathVariable("userId") int userId,
			@PathVariable("productId") int productId) {
		userService.removeFromCart(userId, productId);
		return new ResponseEntity<String>("Products Remove successfully", HttpStatus.OK);
	      }
}
