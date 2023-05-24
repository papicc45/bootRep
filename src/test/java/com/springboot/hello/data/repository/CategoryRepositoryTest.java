package com.springboot.hello.data.repository;

import com.google.common.collect.Lists;
import com.springboot.hello.data.entity.Category;
import com.springboot.hello.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void relationshipTest() {
        Product product = Product.builder().name("펜").price(2000).stock(100).build();
        productRepository.save(product);

        Category category = Category.builder().code("S1").name("도서").products(Lists.newArrayList(product)).build();
        categoryRepository.save(category);

        List<Product>resultList = categoryRepository.findById(1L).get().getProducts();

        for(Product p : resultList) {
            System.out.println(p);
        }
    }
}
