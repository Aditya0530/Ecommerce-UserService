package com.ecommerce.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
private int productId;
}
