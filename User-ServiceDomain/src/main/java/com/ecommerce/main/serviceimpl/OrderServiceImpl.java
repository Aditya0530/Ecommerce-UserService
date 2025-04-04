/*
 * package com.ecommerce.main.serviceimpl;
 * 
 * import java.sql.Date; import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.ecommerce.main.enums.StatusOrder; import
 * com.ecommerce.main.model.Order; import com.ecommerce.main.model.Product;
 * import com.ecommerce.main.model.User; import
 * com.ecommerce.main.repository.OrderRepository; import
 * com.ecommerce.main.servicei.OrderService;
 * 
 * @Service //public class OrderServiceImpl implements OrderService{
 * 
 * @Autowired OrderRepository orderRepository;
 * 
 * @Override public Order createOrder(Order order, List<Product> products) {
 * 
 * Order order1=new Order(); order1.setOrderDate(new
 * Date(System.currentTimeMillis()));
 * order1.setOrderStatus(StatusOrder.CONFIRMED); order1.setProducts(products);
 * 
 * return orderRepository.save(order1); //}
 * 
 * 
 * 
 * }
 */