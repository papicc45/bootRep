package com.springboot.hello.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "provider")
public class Provider extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * 영속성 전이
     * ALL - 모든 영속상태에 대해 전이 적용
     * PERSIST - 엔티티 영속화할 때 연관 엔티티도 영속화
     * MERGE - 엔티티를 연속성 컨텍스트에 병합할 때 연관 엔티티도 병합
     * REMOVE - 엔티티 제거할 때 연관 엔티티 제거
     * REFRESH - 엔티티 새로고침할 때 연관 엔티티 새로고침
     * DETACH - 엔티티를 영속성 컨텍스트에서 제외하면 연관 엔티티도 제외
     */
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)  //EAGER - 즉시로딩 조정
                                                                                                                                                                //orphanRemovel - 고아 객체 제거
    @ToString.Exclude
    private List<Product> productList = new ArrayList<>();
}
