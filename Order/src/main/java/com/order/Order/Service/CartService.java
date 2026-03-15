package com.order.Order.Service;


import com.order.Order.DTO.CartItemRequest;
import com.order.Order.Model.CartItem;
import com.order.Order.Repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository)
    {
        this.cartItemRepository = cartItemRepository;
    }

    public boolean addToCart(Long userId, CartItemRequest request) {

        //Look for product

//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//
//        if(productOpt.isEmpty())
//        {
//            return false;
//        }
//
//        Product product = productOpt.get();
//
//        if(product.getStockQuantity() < request.getQuantity())
//        {
//            return false;
//        }
//
//
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//
//        if(userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, Long.valueOf(request.getProductId()));

        if(existingCartItem != null)
        {
            //update the quantity;
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.0));
            cartItemRepository.save(existingCartItem);
        }
        else {
            //create new cartItem
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItem.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));

            cartItemRepository.save(cartItem);
        }

        return true;

    }

    public boolean deleteItemFromCart(Long userId, Long productId) {

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem != null)
        {
            cartItemRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }

        return false;
    }

    public List<CartItem> getCartOfUser(String userId) {

        return cartItemRepository.findByUserId(Long.valueOf(userId));
    }

    public void clearCart(String userId) {

        cartItemRepository.deleteByUserId(Long.valueOf(userId));

    }
}
