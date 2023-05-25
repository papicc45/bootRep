package com.springboot.hello.data.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity                 //엔티티 명시
@Table(name = "product")  //명시하지 않으면 클래스명과 테이블명이 동일
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true) //callSuper - 부모클래스의 필드를 포함
public class Product extends BaseEntity{

    @Id         //테이블 기본값
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //기본값 생성을 db에 위임
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @OneToOne(mappedBy = "product")  //일대일 양방향 매핑, mappedBy 사용 시 Product 테이블에 ProductDetail 왜래키가 사라짐(객체주인 표시)
    @ToString.Exclude // StackOverFlowError 순환참조 방지
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @ToString.Exclude
    private Provider provider;

    @ManyToMany
    @ToString.Exclude
    private List<Producer> producers = new ArrayList<>();

    public void addProducer(Producer producer) {
        this.producers.add(producer);
    }
    //@Transient - 선언되있는 필드지만 db에서는 필요가 없을 때 사용
}
