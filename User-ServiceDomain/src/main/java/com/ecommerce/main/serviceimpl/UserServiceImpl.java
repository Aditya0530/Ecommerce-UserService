package com.ecommerce.main.serviceimpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.io.ObjectInputFilter.Status;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.eclipse.angus.mail.handlers.image_gif;

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

import com.ecommerce.main.enums.StatusOrder;

import com.ecommerce.main.exceptionhandler.EmailSendingException;

import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.exceptionhandler.ResourceNotFoundException;

import com.ecommerce.main.model.Order;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.ProductReview;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.OrderRepository;
import com.ecommerce.main.repository.ProductRepository;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.EmailService;
import com.ecommerce.main.servicei.UserService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

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
	public List<Product> getByName(String productName) {

		String url = "http://localhost:9292/product/getByName/" + productName;
		List<Product> pro = restTemplate.getForObject(url, List.class);
		return pro;
	}

	/*
	 * @Override public String purchaseProduct(int userId, String productName, int
	 * quantityAvailable) { Optional<User>
	 * userOptional=userRepository.findByUserId(userId); if(userOptional.isEmpty())
	 * { throw new ResourceNotFoundException("No Data Found For the User"); } User
	 * user=userOptional.get();
	 * 
	 * String url="http://localhost:9292/product/getProductByName/"+productName;
	 * Product p=restTemplate.getForObject(url, Product.class); double
	 * totalAmount=0.0; double deliveryCharges=100; Order order=new Order();
	 * if(p.getQuantityAvailable()>=quantityAvailable) {
	 * totalAmount=p.getPrice()*quantityAvailable;
	 * order.setOrderStatus(StatusOrder.CONFIRMED); } double
	 * totalPrice=deliveryCharges+totalAmount; order.setOrderDate(new
	 * Date(System.currentTimeMillis())); List<Product> listProduct=new
	 * ArrayList<>(); listProduct.add(p); order.setProducts(listProduct);
	 * order.setDeliverycharges(deliveryCharges); order.setTotalAmount(totalPrice);
	 * 
	 * List<Order> listOrders=new ArrayList<>(); listOrders.add(order);
	 * user.setOrder(listOrders); userRepository.save(user);
	 * orderRepository.save(order);
	 * 
	 * emailService.sendOrderConfirmationEmail(user, totalAmount, deliveryCharges);
	 * return "Order Placed Successfully"; }
	 * 
	 */
	/*
	 * @Override public String purchaseProduct(int userId, String productName, int
	 * quantityAvailable) { // Fetch user from the database Optional<User>
	 * userOptional = userRepository.findByUserId(userId); if
	 * (userOptional.isEmpty()) { throw new
	 * ResourceNotFoundException("No Data Found For the User"); } User user =
	 * userOptional.get();
	 * 
	 * // Fetch product by name from external service String url =
	 * "http://localhost:9292/product/getProductByName/" + productName; Product p =
	 * restTemplate.getForObject(url, Product.class);
	 * 
	 * if (p == null) { return "Product Not Found"; }
	 * 
	 * // Check if the product quantity is sufficient if (p.getQuantityAvailable() <
	 * quantityAvailable) { return "Insufficient Quantity Available"; }
	 * 
	 * // Calculate total amount double totalAmount = p.getPrice() *
	 * quantityAvailable; double deliveryCharges = 100; // Assuming fixed delivery
	 * charge double totalPrice = totalAmount + deliveryCharges;
	 * 
	 * // Create new order Order order = new Order();
	 * order.setOrderStatus(StatusOrder.CONFIRMED); // Confirmed status
	 * order.setOrderDate(new Date(System.currentTimeMillis())); // Set current date
	 * order.setDeliverycharges(deliveryCharges); order.setTotalAmount(totalPrice);
	 * 
	 * // Add the product to the order List<Product> listProduct = new
	 * ArrayList<>(); listProduct.add(p); // Add product to order
	 * order.setProducts(listProduct);
	 * 
	 * // Add the order to the user's order list List<Order> listOrders = new
	 * ArrayList<>(); listOrders.add(order); //user.setOrder(listOrders);
	 * 
	 * // Save the order and update the user orderRepository.save(order);
	 * //userRepository.save(user); // Send confirmation email to the user
	 * emailService.sendOrderConfirmationEmail(user, totalAmount, deliveryCharges);
	 * 
	 * return "Order Placed Successfully"; }
	 */
	@Override
	public String purchaseProduct(int userId, String productName, int quantityAvailable) {
		// Fetch user from the database
		Optional<User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isEmpty()) {
			throw new ResourceNotFoundException("No Data Found For the User");
		}
		User user = userOptional.get();

		// Fetch product by name from external service
		String url = "http://localhost:9292/product/getProductByName/" + productName;
		Product p = restTemplate.getForObject(url, Product.class);

		if (p == null) {
			return "Product Not Found";
		}

		// Check if the product quantity is sufficient

		if (p.getQuantityAvailable() < quantityAvailable) {
			return "Insufficient Quantity Available";
		}

		// Calculate total amount
		double totalAmount = p.getPrice() * quantityAvailable;
		double deliveryCharges = 100; // Assuming fixed delivery
		double totalPrice = totalAmount + deliveryCharges;

		// Create new order
		Order order = new Order();
		order.setOrderStatus(StatusOrder.CONFIRMED); // Set order status to CONFIRMED
		order.setOrderDate(new Date(System.currentTimeMillis())); // Set the currentdate
		order.setDeliverycharges(deliveryCharges);
		order.setTotalAmount(totalPrice);

		// Add the product to the order
		List<Product> listProduct = new ArrayList<>();
		listProduct.add(p); // Add product to order
		order.setProducts(listProduct); // Set products in the order

		userRepository.save(user); // Save the user with updated orders list

		// Send confirmation email to the user
		orderRepository.save(order);
		emailService.sendOrderConfirmationEmail(user, totalAmount, deliveryCharges);

		// productRepository.save(p);
		return "Order Placed Successfully";
	}

}
