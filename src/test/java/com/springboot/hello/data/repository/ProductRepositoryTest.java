package com.springboot.hello.data.repository;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.hello.config.QueryDSLConfiguration;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.QProduct;
import org.hibernate.tuple.Tuplizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(QueryDSLConfiguration.class)  //슬라이싱 테스트 사용 시 특정부분만 Bean 등록 시 사용, JpaQueryFactory에 사용
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

    private void addDataBase() {
        List<Product> testList = new ArrayList<>();

        for(int i=0 ; i<100 ; i++) {
//            Product product = Product.builder().name("펜").price(i * 10).stock(i).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();  //BaseEntity 미사용시
            Product product = Product.builder().name("펜").price(i * 10).stock(i).build();

            Product savedProduct = productRepository.save(product);
            testList.add(savedProduct);
        }
    }

    @Test
    void sortingAndPagingTest() {
//        Product product1 = Product.builder().name("펜").price(1000).stock(100).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
//        Product product2 = Product.builder().name("펜").price(5000).stock(300).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();  //BaseEntity 미사용시
//        Product product3 = Product.builder().name("펜").price(500).stock(50).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        Product product1 = Product.builder().name("펜").price(1000).stock(100).build();
        Product product2 = Product.builder().name("펜").price(5000).stock(300).build();
        Product product3 = Product.builder().name("펜").price(500).stock(50).build();

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
//            Product product = Product.builder().name("펜").price(i * 10).stock(i).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();  //BaseEntity 미사용시
            Product product = Product.builder().name("펜").price(i * 10).stock(i).build();

            Product savedProduct = productRepository.save(product);
            testList.add(savedProduct);
        }

        List<Product> productList = query.from(qproduct)
                .where(qproduct.name.eq("펜"))
                .orderBy(qproduct.price.desc())
                .fetch();   // 조회결과 리스트로 반환
        /*
            fetchOne 단 건 조회
            fetchFirst 여러 건 중 1건
            fetchCount 조회 결과개수 반환
            QueryResult - 결과 리스트와 개수를 포함해 반환
         */

        for(Product p : productList) {
            System.out.println("-------------------");
            System.out.println("number : " + p.getNumber());
            System.out.println("name : " + p.getName());
            System.out.println("price : " + p.getPrice());
            System.out.println("stock : " + p.getStock());
        }

    }

    @Test
    void queryDslTest2() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);  //jpaQueryFactory는 select절부터 사용
        QProduct qProduct = QProduct.product;

        addDataBase();

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for(String name : productList) {
            System.out.println("name : " + name);
        }

        List<Tuple> tupleList = jpaQueryFactory
                .select(qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for(Tuple tuple : tupleList) {
            System.out.println("name : " + tuple.get(qProduct.name));
            System.out.println("price : " + tuple.get(qProduct.price));
        }
    }

    @Autowired
    JPAQueryFactory jpaQueryFactory;  //config에 빈 등록 후 사용
    @Test
    void queryDslTest3() {
        QProduct qProduct = QProduct.product;

        addDataBase();
        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for(String name : productList) {
            System.out.println("name : " + name);
        }
    }

    @Test
    public void auditingTest() {
        Product product = new Product();
        product.setName("펜");
        product.setPrice(1000);
        product.setStock(100);

        Product savedProduct = productRepository.save(product);

        System.out.println(savedProduct.getName());
        System.out.println(savedProduct.getCreatedAt());
    }

    @BeforeEach
    void generateData() {
        int count = 1;

        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(2000).stock(3000).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(3000).stock(9000).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count--)).price(1500).stock(200).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(10000).stock(5000).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(4000).stock(1500).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(1000).stock(1001).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(500).stock(10000).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(8500).stock(3500).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(7200).stock(2000).build());
        productRepository.save(Product.builder().name("상품" + Integer.toString(count++)).price(5100).stock(1700).build());

    }



    @Test
    void findTest() {
        List<Product>foundAll = productRepository.findAll();
        System.out.println("**** test data ****");
        for(Product p : foundAll) {
            System.out.println(p.toString());
        }

        System.out.println("**** test data ****");

        List<Product> foundByNameList = productRepository.findByName("상품3");
        for(Product p : foundByNameList) {
            System.out.println(p.toString());
        }

    }

    @Test
    void existTest() {
        System.out.println(productRepository.existsByName("상품5"));
    }
}
