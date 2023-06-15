package com.springboot.hello.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShortUrlResponseDTO {

    private String originUrl;

    private String shortUrl;
}
