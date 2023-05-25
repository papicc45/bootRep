package com.springboot.hello.data.repository;

import com.google.common.collect.Lists;
import com.springboot.hello.data.entity.Producer;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.repository.support.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
public class ProducerRepositoryTest {

    @Autowired
    ProducerRepository producerRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    @Transactional      //테스트 데이터 생성 별도로 메서드 구현, 레포지토리 사용 시 트랜잭션 끊어짐 해소
    void relationshipTest() {
        Product product1 = savedProduct("동글펜", 500, 1000);
        Product product2 = savedProduct("네모 공책", 100, 2000);
        Product product3 = savedProduct("지우개", 152, 1234);

        Producer producer1 = savedProducer("flature");
        Producer producer2 = savedProducer("wikibooks");
        producer1.setProducts(new ArrayList<>());
        producer2.setProducts(new ArrayList<>());

        producer1.addProduct(product1);
        producer1.addProduct(product2);

        producer2.addProduct(product2);
        producer2.addProduct(product3);

        producerRepository.saveAll(Lists.newArrayList(producer1, producer2));

        System.out.println(producerRepository.findById(1L).get().getProducts());

    }
    private Product savedProduct(String name, Integer price, Integer stock) {
        Product product = Product.builder().name(name).price(price).stock(stock).build();

        return productRepository.save(product);
    }

    private Producer savedProducer(String name) {
        Producer producer = Producer.builder().name(name).build();

        return producerRepository.save(producer);
    }

    @Test
    @Transactional
    void relationshipTest2() {
        Product product1 = savedProduct("동글펜", 500, 1000);
        Product product2 = savedProduct("네모 공책", 100, 2000);
        Product product3 = savedProduct("지우개", 152, 1234);
        product1.setProducers(new ArrayList<>());
        product2.setProducers(new ArrayList<>());
        product3.setProducers(new ArrayList<>());

        Producer producer1 = savedProducer("flature");
        Producer producer2 = savedProducer("wikibooks");
        producer1.setProducts(new ArrayList<>());
        producer2.setProducts(new ArrayList<>());

        producer1.addProduct(product1);
        producer1.addProduct(product2);

        producer2.addProduct(product2);
        producer2.addProduct(product3);

        product1.addProducer(producer1);
        product2.addProducer(producer1);
        product2.addProducer(producer2);
        product3.addProducer(producer2);

        producerRepository.saveAll(Lists.newArrayList(producer1, producer2));
        productRepository.saveAll(Lists.newArrayList(product1, product2, product3));

        System.out.println("products : " + producerRepository.findById(1L).get().getProducts());
        System.out.println("producers : "  + productRepository.findById(2L).get().getProducers());
    }
}
