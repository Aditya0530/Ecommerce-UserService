package com.ecommerce.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
public int orderId;
}
