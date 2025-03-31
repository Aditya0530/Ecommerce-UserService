package com.ecommerce.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.User;
import com.ecommerce.main.servicei.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
UserService userService;

@PostMapping("/save_User")
public ResponseEntity<UserDto> saveProduct(@RequestBody User user){
UserDto userDto=userService.saveUser(user);
return new ResponseEntity<UserDto>(userDto,HttpStatus.CREATED);
}
}
