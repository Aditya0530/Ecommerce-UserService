package com.ecommerce.main.model;

import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.ecommerce.main.enums.StatusOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId; // Auto-increment enabled

	private double totalAmount;

	@Enumerated(EnumType.STRING)
	private StatusOrder orderStatus; // Pending, Confirmed, Cancelled

	//auto save creation timestamp
	@CreationTimestamp
	private Date orderDate;

	private double deliverycharges;
	/*
	 * @OneToOne(cascade = CascadeType.MERGE) private Product product;
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", referencedColumnName = "productId") // FK in orders table
	private Product product;

	
}
