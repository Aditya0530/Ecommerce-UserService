package com.ecommerce.main.dto;

import com.ecommerce.main.model.Product;

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
}
