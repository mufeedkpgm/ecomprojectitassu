package com.itassu.ecomproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itassu.ecomproject.entity.CartItems;
import com.itassu.ecomproject.entity.Order;
import com.itassu.ecomproject.enums.OrderStatus;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long>{
	
	Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId, Long orderId, Long userId);
	
	Optional<CartItems> findByProductIdAndOrderId(Long productId, Long orderId);
	

}
