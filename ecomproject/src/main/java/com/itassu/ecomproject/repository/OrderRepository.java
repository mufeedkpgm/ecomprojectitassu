package com.itassu.ecomproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itassu.ecomproject.entity.Order;
import com.itassu.ecomproject.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

}
