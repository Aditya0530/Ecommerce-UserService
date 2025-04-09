package com.ecommerce.main.serviceimpl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.exceptionhandler.DuplicateProductFoundException;
import com.ecommerce.main.exceptionhandler.EmailSendingException;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.exceptionhandler.UserIdNotFoundException;
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
		} else {
			List<User> user = (List<User>) userRepository.findAllByUsernameAndPassword(username, password);

			if (user.isEmpty()) {
				throw new InvalidCredentialsException("Username And Password Incorrect");
			} else {
				
				String userEmail = user.get(0).getEmail();
				
                try {
                	
                    emailService.sendEmail(userEmail, "Login Successful", "You have successfully logged in.");
                    
                } catch (EmailSendingException e) {
                	
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
	public Iterable<Product> getByName(String productName) {
	    String url = "http://localhost:9292/product/getByName/" + productName;
	    Iterable<Product> pro=restTemplate.getForObject(url, Iterable.class);
	    return pro;
	}

	@Override
	public void addToCart(int userId, int productId) {

	    Map<String, Object> productFromProductModule = restTemplate.getForObject("http://localhost:9292/product/getById/" + productId, Map.class);       
	    User user = userRepository.findById(userId).orElseThrow(()-> new UserIdNotFoundException("Please input correct user id"));
        int newProductId=(Integer)productFromProductModule.get("productId");
	   
        for(Product product : user.getProduct()){
	    	
	    	if(product.getProductId()==newProductId) {
	    		
	    		throw new DuplicateProductFoundException("Product id already present");
	    	}
	    }
 List<Map<String, Object>> productImages =  (List<Map<String, Object>>) productFromProductModule.get("productImages");
          
               Map<String, Object> productImageMap = productImages.get(0);
              String imageDataString = (String) productImageMap.get("imageData");
              byte[]   imageData = Base64.getDecoder().decode(imageDataString);

               Product productToUpdate = new Product();
          productToUpdate.setProductId((Integer)productFromProductModule.get("productId"));
            productToUpdate.setProductName((String)productFromProductModule.get("productName"));
          productToUpdate.setDescription((String)productFromProductModule.get("description"));
           productToUpdate.setBrand((String)productFromProductModule.get("brand"));
            productToUpdate.setPrice((Double)productFromProductModule.get("price"));
           
                productToUpdate.setImage(imageData);
       	 user.getProduct().add(productToUpdate);
         userRepository.save(user);

               }
}
            
            
          