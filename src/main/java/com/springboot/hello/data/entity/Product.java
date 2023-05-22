package com.springboot.hello.data.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity                 //엔티티 명시
@Table(name = "product")  //명시하지 않으면 클래스명과 테이블명이 동일
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Product {

    @Id         //테이블 기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //기본값 생성을 db에 위임
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //@Transient - 선언되있는 필드지만 db에서는 필요가 없을 때 사용
}
