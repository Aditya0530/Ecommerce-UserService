package com.ecommerce.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.servicei.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
UserService userService;

@PostMapping("/save_User")
public ResponseEntity<UserDto> saveProduct(@Valid @RequestBody User user){
UserDto userDto=userService.saveUser(user);
return new ResponseEntity<UserDto>(userDto,HttpStatus.CREATED);
}

@GetMapping("/login/{username}/{password}")
public ResponseEntity<Iterable<User>> login(@PathVariable("username") String username, @PathVariable("password") String password) {
 Iterable<User> user = userService.loginUser(username, password);
    return new ResponseEntity<>(user, HttpStatus.OK);
}

@GetMapping("/search_Product") 
public ResponseEntity<Iterable<Product>> getAll(){
	Iterable<Product> p=userService.getAll();
	 return new ResponseEntity<>(p, HttpStatus.OK);
}

@GetMapping("/search_ProductByName/{productName}") 
public ResponseEntity <Iterable<Product>> getproductByName(@PathVariable("productName") String productName){
	Iterable<Product> p=userService.getByName(productName);
	 return new ResponseEntity<Iterable<Product>>(p, HttpStatus.OK);
}

@PutMapping("/add/{userId}/{productName}")
public ResponseEntity<String> updateUserProducts(@PathVariable("userId") int userId, @PathVariable("productName") String productName) {
    userService.addToCart(userId, productName);
    return new ResponseEntity<String>("Products updated successfully",HttpStatus.OK);
}

}

