package com.springboot.hello.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDTO {

    private String name;
    private int price;
    private int stock;
}
