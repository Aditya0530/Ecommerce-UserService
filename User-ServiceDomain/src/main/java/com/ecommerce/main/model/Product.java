package com.ecommerce.main.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
private String category;
private double price;
private int quantityAvailable;
private String supplierName;
private String supplierContact;
private int warrantyPeriod;
private boolean available;

}             
