package com.ecommerce.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.servicei.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
UserService userService;
@Autowired
RestTemplate resttemp;

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
@PostMapping("/addtocart_Product/{userId}/{productId}")
public ResponseEntity<String> addtoCart(@PathVariable("userId") int userId,@PathVariable("productId")int pId){
String url="http://localhost:9292/product/getAll";
List l=resttemp.getForObject(url, List.class);

if(userService.getUser(userId)!=null) {
 userService.addtocart(l);	
}
else {
throw new InvalidCredentialsException("User Id did not match,login to continue");
}
return new ResponseEntity<>("Product Added",HttpStatus.FOUND);
	 
}
}
