package com.ecommerce.main.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@NotBlank(message = "Name cannot be empty")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	private String name;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;

	@NotBlank(message = "Username cannot be empty")
	@Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
	private String username;

	@NotBlank(message = "Address cannot be empty")
	@Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
	private String address;
	
	@NotNull(message = "Mobile number cannot be null")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private long mobileno;
	
	@OneToMany(cascade = CascadeType.MERGE) // patch method for user // logincheck //getAll //post only user
	private List<Product> product;
	@OneToMany(cascade = CascadeType.MERGE)
	private List<Order> order;

}
