package com.springboot.hello.data.repository.support;

import com.springboot.hello.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void findByNameTest() {
        addDataBase();
        List<Product> productList = productRepository.findByName("펜");

        for(Product p : productList) {
            System.out.println("name : " + p.getName());
            System.out.println("price : " + p.getPrice());
        }
    }

    private void addDataBase() {
        List<Product> testList = new ArrayList<>();

        for(int i=0 ; i<100 ; i++) {
//            Product product = Product.builder().name("펜").price(i * 10).stock(i).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(); //BaseEntity 미사용시
            Product product = Product.builder().name("펜").price(i * 10).stock(i).build();

            Product savedProduct = productRepository.save(product);
            testList.add(savedProduct);
        }
    }
}
