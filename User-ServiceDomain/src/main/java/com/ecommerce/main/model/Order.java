package com.ecommerce.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="orders")
public class Order {
	@Id
	public int orderId;
}
