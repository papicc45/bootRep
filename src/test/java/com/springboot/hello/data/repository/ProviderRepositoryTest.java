package com.springboot.hello.data.repository;

import com.google.common.collect.Lists;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
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

    private Provider savedProvider(String name) {
        Provider provider = new Provider();
        provider.setName(name);

        return provider;
    }

    private Product savedProduct(String name, Integer price, Integer stock) {
        Product product = Product.builder().name(name).price(price).stock(stock).build();

        return product;
    }
    @Test
    void cascadeTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1500);
        Product product2 = savedProduct("상품2", 500, 1000);
        Product product3 = savedProduct("상품3", 750, 500);

        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepositroy.save(provider);

    }

    @Test
    @Transactional
    void orphanRemovalTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1500);
        Product product2 = savedProduct("상품2", 500, 1000);
        Product product3 = savedProduct("상품3", 750, 500);

        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepositroy.saveAndFlush(provider);

        providerRepositroy.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);

        Provider foundProvider = providerRepositroy.findById(1L).get();
        foundProvider.getProductList().remove(0);

        providerRepositroy.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);
    }
}
