package com.ecommerce.main.serviceimpl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.enums.StatusOrder;
import com.ecommerce.main.exceptionhandler.DuplicateProductFoundException;
import com.ecommerce.main.exceptionhandler.EmailSendingException;
import com.ecommerce.main.exceptionhandler.InvalidCredentialsException;
import com.ecommerce.main.exceptionhandler.UserIdNotFoundException;
import com.ecommerce.main.model.Order;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.OrderRepository;

import com.ecommerce.main.repository.ProductRepository;
import com.ecommerce.main.repository.UserRepository;
import com.ecommerce.main.servicei.EmailService;
import com.ecommerce.main.servicei.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private EmailService emailService;

	private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto saveUser(User user) {
		LOGGER.info("Saving user: {}", user.getUsername());
		userRepository.save(user);
		LOGGER.info("User saved successfully: {}", user.getUsername());
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
		Iterable<Product> response = restTemplate.getForObject(url, Iterable.class);
		return response;
	}

	@Override
	public User getUser(int userId) {
		User user = userRepository.findAllByUserId(userId);
		return user;
	}

	@Override
	public Iterable<Product> getByName(String productName) {
		String url = "http://localhost:9292/product/getByName/" + productName;
		Iterable<Product> product = restTemplate.getForObject(url, Iterable.class);
		return product;
	}

	@Override
	public void addToCart(int userId, int productId) {

		Map<String, Object> productFromProductModule = restTemplate
				.getForObject("http://localhost:9292/product/getById/" + productId, Map.class);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserIdNotFoundException("Please input correct user id"));
		int newProductId = (Integer) productFromProductModule.get("productId");

		for (Product product : user.getProduct()) {

			if (product.getProductId() == newProductId) {
				LOGGER.warn("Duplicate product found in cart for user {}", userId);
				throw new DuplicateProductFoundException("Product id already present");
			}
		}
		List<Map<String, Object>> productImages = (List<Map<String, Object>>) productFromProductModule
				.get("productImages");

		Map<String, Object> productImageMap = productImages.get(0);
		String imageDataString = (String) productImageMap.get("imageData");
		byte[] imageData = Base64.getDecoder().decode(imageDataString);

		Product productToUpdate = new Product();
		productToUpdate.setProductId((Integer) productFromProductModule.get("productId"));
		productToUpdate.setProductName((String) productFromProductModule.get("productName"));
		productToUpdate.setDescription((String) productFromProductModule.get("description"));
		productToUpdate.setBrand((String) productFromProductModule.get("brand"));
		productToUpdate.setPrice((Double) productFromProductModule.get("price"));
		productToUpdate.setImage(imageData);
		user.getProduct().add(productToUpdate);
		userRepository.save(user);
		LOGGER.info("Product with ID {} added to cart for user {}", productId, userId);

	}

	@Override
	@Transactional
	public String placeOrder(int userId, int productId, Order order) {
		LOGGER.info("Purchase request by user {} for product {}", userId, productId);

		String productUrl = "http://localhost:9292/product/getById/" + productId;
		Product productFromService = restTemplate.getForObject(productUrl, Product.class);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserIdNotFoundException("Please input correct user ID"));

		Product managedProduct = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found in database"));

		List<Order> existingOrder = getByUserIdProductId(userId, productId);
		if (!existingOrder.isEmpty()) {
			LOGGER.warn("User {} already ordered product {}", userId, productId);
			Order currentOrder = existingOrder.get(0);
			currentOrder.setTotalAmount(managedProduct.getPrice());
			orderRepository.save(currentOrder);
			return "You Already Ordered This Product";
		} else {
			double totalAmount = managedProduct.getPrice() * order.getQuantity();
			order.setOrderStatus(StatusOrder.CONFIRMED);
			order.setTotalAmount(totalAmount);
			order.setDeliverycharges(100);
			order.setProduct(managedProduct);
			user.getProduct().add(managedProduct);
			user.getOrder().add(order);

			userRepository.save(user);
			LOGGER.info("Order placed successfully by user {} for product {}", userId, productId);

			emailService.sendOrderConfirmationEmail(user, order);
			LOGGER.info("Order confirmation email sent to user {}", userId);
			return "Order Placed Successfully";

		}
	}

	@Override
	public List<Order> getByUserIdProductId(int userId, int productId) {

		return orderRepository.findByUserIdAndProductId(userId, productId);
	}

	@Override
	public void orderStatus(int orderId, StatusOrder orderStatus) {

		orderRepository.updateOrderStatus(orderId, orderStatus);
	}

	double totalAmount = 0.0;
	double deliveryCharges = 0.0;
	double grandTotal = 0.0;

	@Override
	public Map<String, Object> viewCart(int userId) {

		List<Product> productResponse = getProductByUserId(userId);

		List<Order> orderResponse = getOrderByUserId(userId);

		List<Map<String, Object>> listProduct = new ArrayList<>();

		Map<String, Object> responseMap = new LinkedHashMap<>();

		for (Order order : orderResponse) {
			Product product = order.getProduct();
			Map<String, Object> orderedProduct = new LinkedHashMap<>();

			orderedProduct.put("productId", product.getProductId());
			orderedProduct.put("productName", product.getProductName());
			orderedProduct.put("productImg", product.getImage());
			orderedProduct.put("productBrand", product.getBrand());
			orderedProduct.put("productQuantity", order.getQuantity());
			orderedProduct.put("deliveryCharges", order.getDeliverycharges());
			orderedProduct.put("productPrice", product.getPrice());

			totalAmount += product.getPrice() * order.getQuantity();
			deliveryCharges += order.getDeliverycharges();
			grandTotal = totalAmount + deliveryCharges;

			listProduct.add(orderedProduct);
		}
		double totalPrice = 0.0;
		for (Product product : productResponse) {
			totalPrice = product.getPrice();
		}
		responseMap.put("orderSummary", Map.of("grandTotal", grandTotal, "orderedProduct", listProduct));
		responseMap.put("cartSummary", Map.of("totalPrice", totalPrice, "cartProduct", productResponse));
		return responseMap;
	}
	@Override
	public void removeFromCart(int userId, int productId) {
	   
		User user = userRepository.findById(userId).orElseThrow(()-> new UserIdNotFoundException("Please input correct user id"));
        user.getProduct().removeIf(product -> product.getProductId()==productId);
        productRepository.deleteById(productId);
        
	}
	@Override
	public List<Order> getOrderByUserId(int userId) {

		return orderRepository.getOrdersByUserId(userId);
	}

	@Override
	public List<Product> getProductByUserId(int userId) {

		return productRepository.getProductsByUserId(userId);
	}
 }
