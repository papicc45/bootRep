package com.springboot.hello.data.repository;

import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProviderRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProviderRepositroy providerRepositroy;

    @Test
    void relationshipTest1() {
        Provider provider = Provider.builder().name("OO물산").build();
        providerRepositroy.save(provider);

        Product product = Product.builder().name("가위").price(5000).stock(500).provider(provider).build();
        productRepository.save(product);

        System.out.println("product : " + productRepository.findById(1L).orElseThrow(RuntimeException::new));
        System.out.println("provider : " + productRepository.findById(1L).orElseThrow(RuntimeException::new).getProvider());
    }

    @Test
    void relationshipTest2() {
        Provider provider = Provider.builder().name("OO상사").build();
        providerRepositroy.save(provider);

        Product product1 = Product.builder().name("펜").price(2000).stock(100).provider(provider).build();
        Product product2 = Product.builder().name("가방").price(20000).stock(30).provider(provider).build();
        Product product3 = Product.builder().name("노트").price(1000).stock(800).provider(provider).build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> productList = providerRepositroy.findById(provider.getId()).get().getProductList();

        for(Product p : productList) {
            System.out.println(p);
        }

    }
}
