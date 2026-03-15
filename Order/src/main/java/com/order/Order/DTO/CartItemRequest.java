package com.order.Order.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemRequest {

    private String productId;
    private Integer quantity;

}
