package com.itassu.ecomproject.services.customer.cart;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itassu.ecomproject.dto.AddProductInCartDto;
import com.itassu.ecomproject.dto.CartItemsDto;
import com.itassu.ecomproject.dto.OrderDto;
import com.itassu.ecomproject.entity.CartItems;
import com.itassu.ecomproject.entity.Order;
import com.itassu.ecomproject.entity.Product;
import com.itassu.ecomproject.entity.User;
import com.itassu.ecomproject.enums.OrderStatus;
import com.itassu.ecomproject.repository.CartItemRepository;
import com.itassu.ecomproject.repository.OrderRepository;
import com.itassu.ecomproject.repository.ProductRepository;
import com.itassu.ecomproject.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInDto) {
        // Retrieve or create an active order
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInDto.getUserId(), OrderStatus.Pending);

        if (activeOrder == null) {
            Optional<User> optionalUser = userRepository.findById(addProductInDto.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            User user = optionalUser.get();
            activeOrder = createNewOrder(user); // Create a new order if not found
        }

        // Proceed with adding or updating cart item
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInDto.getProductId(), activeOrder.getId(), addProductInDto.getUserId());

        if (optionalCartItems.isPresent()) {
            // Update the quantity of the existing cart item
            CartItems existingCartItem = optionalCartItems.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);

            // Update the order totals
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + existingCartItem.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + existingCartItem.getPrice());
            orderRepository.save(activeOrder);

            return ResponseEntity.status(HttpStatus.OK).body("Product quantity updated in cart");
        } else {
            // Add new product to the cart
            Optional<Product> optionalProduct = productRepository.findById(addProductInDto.getProductId());
            if (optionalProduct.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            Product product = optionalProduct.get();
            CartItems cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(1L);
            cartItem.setUser(activeOrder.getUser());
            cartItem.setOrder(activeOrder);

            cartItemRepository.save(cartItem);

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
            activeOrder.getCartItems().add(cartItem);
            orderRepository.save(activeOrder);

            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart");
        }
    }

    private Order createNewOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.Pending);
        order.setDate(new Date());
        order.setTotalAmount(0L); // Initialize as needed
        order.setAmount(0L); // Initialize as needed
        orderRepository.save(order);
        return order;
    }
    
    public OrderDto getCartByUserId(Long userId) {
    	Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
    	List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
    	OrderDto orderDto = new OrderDto();
    	orderDto.setAmount(activeOrder.getAmount());
    	orderDto.setId(activeOrder.getId());
    	orderDto.setOrderStatus(activeOrder.getOrderStatus());
    	orderDto.setDiscount(activeOrder.getDiscount());
    	orderDto.setTotalAmount(activeOrder.getTotalAmount());
    	orderDto.setCartItems(cartItemsDtoList);;
    	
    	return orderDto;
    }
}

