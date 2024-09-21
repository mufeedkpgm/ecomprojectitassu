package com.itassu.ecomproject.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itassu.ecomproject.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
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
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    private List<CartItems> cartItems = new ArrayList<>();
    
    public void addCartItem(CartItems cartItem) {
        cartItems.add(cartItem);
        cartItem.setOrder(this);
    }

    public void removeCartItem(CartItems cartItem) {
        cartItems.remove(cartItem);
        cartItem.setOrder(null);
    }
}

