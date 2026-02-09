package com.order.Order.Controller;



import com.order.Order.DTO.OrderResponse;
import com.order.Order.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Optional<OrderResponse>> createOrder(
            @RequestHeader("X-User-ID") String userId
    )
    {
        Optional<OrderResponse> orderResponse = orderService.placeAnOrder(userId);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }



}
