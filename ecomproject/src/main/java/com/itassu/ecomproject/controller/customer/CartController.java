package com.itassu.ecomproject.controller.customer;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itassu.ecomproject.dto.AddProductInCartDto;
import com.itassu.ecomproject.dto.OrderDto;
import com.itassu.ecomproject.services.customer.cart.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	
	
	@PostMapping("/cart")
	public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
	    return cartService.addProductToCart(addProductInCartDto);
	}
	
	@GetMapping("/cart/{userId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
		System.out.println("Received userId: " + userId);
		OrderDto orderDto = cartService.getCartByUserId(userId);
	    return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}



}
