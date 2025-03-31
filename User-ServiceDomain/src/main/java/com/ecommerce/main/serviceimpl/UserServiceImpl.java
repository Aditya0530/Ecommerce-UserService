package com.ecommerce.main.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.UserService;

@Service
public class UserServiceImpl implements UserService {
@Autowired
UserRepository userRepository;

@Override
public UserDto saveUser(User user) {
userRepository.save(user);
return new UserDto(user) ;
}
}
