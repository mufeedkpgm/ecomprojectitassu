package com.itassu.ecomproject.dto;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.itassu.ecomproject.enums.OrderStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class OrderDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderDescription;
	
	private Date date;
	
	private Long amount;
	
	private String address;
	
	private String payment;
	
	private OrderStatus orderStatus;
	
	private Long totalAmount;
	
	private Long discount;
	
	private UUID trackingId; 
	
	
	private String userName;
	
	
	private List<CartItemsDto> cartItems;
	

}
