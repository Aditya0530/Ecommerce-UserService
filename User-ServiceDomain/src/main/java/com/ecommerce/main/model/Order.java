package com.ecommerce.main.model;

import java.io.ObjectInputFilter.Status;
import java.sql.Date;
import java.util.List;

import com.ecommerce.main.enums.StatusOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
public int orderId;
private double totalAmount;
@Enumerated(EnumType.STRING)
private StatusOrder orderStatus;      //Pending, Confirmed, Cancelled
private Date orderDate;
@OneToOne(cascade = CascadeType.ALL)
private Address address;
private long contactNo;  
private double deliverycharges;  // when we add the oreders then total charges add and 
@OneToMany(cascade=CascadeType.ALL)
private List<Product> product;

// Getters and Setters
}


