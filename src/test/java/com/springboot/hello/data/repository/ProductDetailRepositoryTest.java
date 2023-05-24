package com.springboot.hello.data.repository;

import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDetailRepositoryTest {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void saveAndReadTest1() {
        Product product = Product.builder().name("스프링 부트 JPA").price(1500).stock(500).build();
        productRepository.save(product);

        ProductDetail productDetail = ProductDetail.builder().product(product).description("스프링 부트와 JPA를 함께 볼수 있는 책").build();
        productDetailRepository.save(productDetail);

        System.out.println(productDetailRepository.findById(productDetail.getId()).get().getProduct());
        System.out.println(productDetailRepository.findById(productDetail.getId()).get());
    }
}
