package com.ecommerce.main.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Override
public UserDto saveUser(User user) {
userRepository.save(user);
return new UserDto(user) ;
}

@Override
public Iterable<User> loginUser(String username, String password) {
	
	if("Admin".equalsIgnoreCase(username) && "Admin".equalsIgnoreCase(password)) {
		
	
	return userRepository.findAll();
}
    else {
		List<User> user = (List<User>) userRepository.findAllByUsernameAndPassword(username, password);
		
		if(user.isEmpty()) {
			throw new InvalidCredentialsException("Username And Password Incorrect");
		}
		else {
			return user;
		}
	}
}

}
