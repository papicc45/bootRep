package com.springboot.hello.data.repository;

import com.querydsl.core.types.Predicate;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.QProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class QProductRepositoryTest {

    @Autowired
    QProductRepository qProductRepository;

    private void addDataBase() {
        List<Product> testList = new ArrayList<>();

        for(int i=0 ; i<100 ; i++) {
//            Product product = Product.builder().name("펜").price(i * 10).stock(i).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(); //BaseEntity 미사용시
            Product product = Product.builder().name("펜").price(i * 10).stock(i).build();
            Product savedProduct = qProductRepository.save(product);
            testList.add(savedProduct);
        }
    }
    @Test
    public void queryDslTest1() {
        Predicate predicate = QProduct.product.name.containsIgnoreCase("펜").and(QProduct.product.price.between(1000, 2500));
//        Product addProduct = Product.builder().name("펜").price(1239).stock(100).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(); //BaseEntity 미사용시
        Product addProduct = Product.builder().name("펜").price(1239).stock(100).build();
        qProductRepository.save(addProduct);
        Optional<Product> foundProduct = qProductRepository.findOne(predicate);

        if(foundProduct.isPresent()) {
            Product product = foundProduct.get();
            System.out.println(product.getNumber());
            System.out.println(product.getPrice());
            System.out.println(product.getName());
            System.out.println(product.getStock());
        }
    }


}
