package com.springboot.hello.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeProductNameDTO {

    private Long number;
    private String name;
}
