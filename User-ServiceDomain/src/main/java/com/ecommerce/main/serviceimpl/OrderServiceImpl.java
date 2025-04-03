package com.ecommerce.main.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.main.model.Address;
import com.ecommerce.main.model.Order;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.repository.OrderRepository;
import com.ecommerce.main.servicei.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	OrderRepository orderRepository;
	@Override
	public Order createOrder(Order order, List<Product> products, Address address) {
		List<Product> requestedProducts=new ArrayList<>();
		for(Product reQuest:requestedProducts) {
		 String url = "http://localhost:9292/product/getByName/" + reQuest.getProductName();
		 List<Product> pro=restTemplate.getForObject(url, List.class);
		 if(!pro.isEmpty()) {
		 requestedProducts.addAll(pro);
		 }
		order.setContactNo(1457123698);
	     Address addrs=new Address();
		 addrs.setAreaName("PUNE");
		 addrs.setAreaName("Hinjewadi");
		 addrs.setCountryName("India");
		 addrs.setPinCode(422010);
		 order.setAddress(address);
		 order.setProduct(requestedProducts);
		return orderRepository.save(order);
	}
		return order;

	}
}
