package com.springboot.hello.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@MappedSuperclass       //JPA의 엔티티 클래스가 상속받을 경우 자식클래스에게 매핑 정보 전달
@EntityListeners(AuditingEntityListener.class)      //EntityListeners - 엔티티를 데이터베이스에 적용하기 전후로 콜백 요청할 수 있게 함
                                                                            //AuditingEntityListener - 엔티티의 Auditing 정보를 주입하는 JPA 엔티티리스너 클래스
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
