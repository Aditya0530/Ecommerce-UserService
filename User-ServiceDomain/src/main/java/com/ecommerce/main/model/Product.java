package com.ecommerce.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
private int productId;
}
