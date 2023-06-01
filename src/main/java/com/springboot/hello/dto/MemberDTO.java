package com.springboot.hello.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class MemberDTO {
    private String name;
    private String email;
    private String organization;


}
