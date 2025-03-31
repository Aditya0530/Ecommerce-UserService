package com.ecommerce.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UOrder {
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
public int orderId;
}
