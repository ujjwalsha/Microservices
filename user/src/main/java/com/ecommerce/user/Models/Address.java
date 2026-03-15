package com.ecommerce.user.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collation = "address")
public class Address {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
