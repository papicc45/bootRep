package com.springboot.hello.data.repository;


import com.springboot.hello.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

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
    }


}
