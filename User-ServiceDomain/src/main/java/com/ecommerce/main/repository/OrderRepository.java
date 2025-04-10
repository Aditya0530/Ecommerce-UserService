package com.ecommerce.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.main.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

	
	/*
	 * @Query(value =
	 * "SELECT o.* FROM `orders` o JOIN user_order uo ON o.order_id = uo.order_order_id JOIN user u ON uo.user_user_id = u.user_id WHERE u.user_id = :userId AND o.product_product_id = :productId"
	 * , nativeQuery = true) Order findOrderByUserIdAndProductId(@Param("userId")
	 * int userId, @Param("productId") int productId);
	 */
	
	//@Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1 AND o.product_id = ?2", nativeQuery = true)
	//Optional<Order> findByUserIdAndProductId(int userId, int productId);
	
	@Query(value = "SELECT * FROM orders WHERE user_id = :userId AND product_id = :productId", nativeQuery = true)
	Optional<Order> findByUserIdAndProductId(@Param("userId") int userId, @Param("productId") int productId);

}
