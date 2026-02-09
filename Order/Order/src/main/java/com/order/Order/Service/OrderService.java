package com.order.Order.Service;



import com.order.Order.DTO.OrderItemDTO;
import com.order.Order.DTO.OrderResponse;
import com.order.Order.Model.CartItem;
import com.order.Order.Model.Order;
import com.order.Order.Model.OrderItem;
import com.order.Order.Model.OrderStatus;
import com.order.Order.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartService cartService)
    {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Optional<OrderResponse> placeAnOrder(String userId) {

        //validate with cartitem

        List<CartItem> cartItemList = cartService.getCartOfUser(userId);
        if(cartItemList.isEmpty())
        {
            return Optional.empty();
        }

        //validate for user
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty())
//        {
//
//        }

        // the Stream API provides a declarative, pipeline-based way to process data
        // (filter, map, sort, reduce) with lazy evaluation.
        //Source: A collection/array/file that produces elements.
        //Intermediate operations: Transform the stream (e.g., filter, map, sorted) — lazy.
        //Terminal operations: Trigger evaluation (e.g., collect, forEach, reduce).

       // User user = userOpt.get();
        //calculate totalpriced
        BigDecimal totalPrice = cartItemList.stream()
                .map(CartItem::getPrice)  //CartItem::getPrice is shorthand for item -> item.getPrice().
                .reduce(BigDecimal.ZERO, BigDecimal::add);  //Shorthand for (a, b) -> a.add(b).
        //create order
        Order order = new Order();
        order.setUserId(Long.valueOf(userId));
        order.setTotalAmount(totalPrice);

        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItemList.stream()
                .map(item ->new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();

        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        //clear the cart

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));



    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem-> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))

                        )).toList(),
                order.getCreatedAt()
        );
    }
}
