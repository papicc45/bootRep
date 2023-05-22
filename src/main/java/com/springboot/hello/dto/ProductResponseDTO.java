package com.springboot.hello.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductResponseDTO {
    private Long number;
    private String name;
    private int price;
    private int stock;
}
