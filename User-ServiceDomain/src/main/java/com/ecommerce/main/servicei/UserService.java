package com.ecommerce.main.servicei;
import org.springframework.stereotype.Service;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

public interface UserService {
public UserDto saveUser(User user);

public Iterable<User> loginUser(String username, String password);

}
