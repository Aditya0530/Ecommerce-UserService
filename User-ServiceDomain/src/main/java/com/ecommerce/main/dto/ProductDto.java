package com.ecommerce.main.dto;

import java.util.List;

import com.ecommerce.main.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDto{
	
    private int productId;
    private String productName;
    private String description;
    private String brand;
    private double price; 

    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.brand = product.getBrand();
        this.price = product.getPrice();
    }

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
}
