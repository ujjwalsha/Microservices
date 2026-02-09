package com.product.product.DTO;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String name;
    private String Description;
    private BigDecimal Price;
    private Integer stockQuantity;
    public String category;
    private String imageUrl;

}
