package com.ecommerce.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
private int productId;// add to cart // remove from cart  //  
private String productName;
private String description;
private String brand;
private double price;

@Lob
@Column(length = 999999999)
private byte[] image;


}             
