package com.itassu.ecomproject.services.customer.cart;

import org.springframework.http.ResponseEntity;

import com.itassu.ecomproject.dto.AddProductInCartDto;
import com.itassu.ecomproject.dto.OrderDto;

public interface CartService {
	
	ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInDto);
	
	OrderDto getCartByUserId(Long userId);

}
