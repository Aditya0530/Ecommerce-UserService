package com.ecommerce.main.model;

import java.sql.Date;
import java.util.List;

import com.ecommerce.main.enums.StatusOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

	private Date orderDate;

	private double deliverycharges;

	@OneToMany(cascade = CascadeType.ALL) // ✅ Products will be saved inside Order
	private List<Product> products;
}
