package com.order.Order.Controller;


import com.order.Order.DTO.CartItemRequest;
import com.order.Order.Model.CartItem;
import com.order.Order.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request )
    {
       if(!cartService.addToCart(Long.valueOf(userId), request))
       {
           return ResponseEntity.badRequest()
                   .body("product out of stock");
       }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("cartItem/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean deleted = cartService.deleteItemFromCart(Long.valueOf(userId), productId);

        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartOfUser(@RequestHeader("X-User-ID") String userId)
    {
        List<CartItem> cartItemList = cartService.getCartOfUser(userId);

        return ResponseEntity.ok(cartItemList);
    }


}
