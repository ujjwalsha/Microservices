package com.order.Order.Repository;


import com.order.Order.Model.CartItem;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserIdAndProductId(@Param("user") Long userId, @Param("product") Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    List<CartItem> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
