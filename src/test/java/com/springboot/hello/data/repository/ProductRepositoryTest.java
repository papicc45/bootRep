package com.springboot.hello.data.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.QProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Replace.ANY = 임베디드 메모리 DB 사용, NONE = 실제 사용하는 DB 사용
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product product = Product.builder().name("펜").price(1000).stock(1000).build();

        Product savedProduct = productRepository.save(product);

        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getStock(), savedProduct.getStock());
    }

    @Test
    void sortingAndPagingTest() {
        Product product1 = Product.builder().name("펜").price(1000).stock(100).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        Product product2 = Product.builder().name("펜").price(5000).stock(300).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        Product product3 = Product.builder().name("펜").price(500).stock(50).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price")));
        productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock")));

        Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0, 2));
        List<Product> contents = productPage.getContent();
        for(Product p : contents) {
            System.out.println(p.getPrice());
        }

    }

    @PersistenceContext
    EntityManager entityManager;
    @Test
    void queryDslTest() {
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        QProduct qproduct = QProduct.product;

        List<Product> testList = new ArrayList<>();

        for(int i=0 ; i<100 ; i++) {
            Product product = Product.builder().name("펜").price(i * 10).stock(i).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

            Product savedProduct = productRepository.save(product);
            testList.add(savedProduct);
        }

        List<Product> productList = query.from(qproduct)
                .where(qproduct.name.eq("펜"))
                .orderBy(qproduct.price.desc())
                .fetch();

        for(Product p : productList) {
            System.out.println("-------------------");
            System.out.println("number : " + p.getNumber());
            System.out.println("name : " + p.getName());
            System.out.println("price : " + p.getPrice());
            System.out.println("stock : " + p.getStock());
        }

    }

}
