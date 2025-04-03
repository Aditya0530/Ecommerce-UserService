package com.ecommerce.main.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
private boolean available;
private int quantityAvailable;

/*
 * @OneToMany(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "product_id")
 */
/*
 * @ElementCollection private List<ProductImage> productImages;
 */ //single image 

@OneToOne
private ProductImage productImage;

/*
 * @OneToMany(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "product_id")
 */
@Transient
private List<ProductFeatures> productFeatures;

/*
 * @OneToMany(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "product_id")
 */
//@ElementCollection
@Transient
private List<ProductReview> productReviews;
}             
