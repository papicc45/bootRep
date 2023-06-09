package com.springboot.hello.data.repository;

import com.springboot.hello.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNumber(Long number);
    List<Product> findAllByName(String name);
    Product queryByNumber(Long number);

    boolean existsByNumber(Long number);
    boolean existsByName(String name);
    long countByName(String name);

    void deleteByNumber(Long number);
    long removeByName(String name);

    List<Product> findFirst5ByName(String name);
    List<Product> findTop10ByName(String name);

    Product findByNumberIs(Long number);
    Product findByNumberEquals(Long number);

    Product findByNumberIsNot(Long number);
    Product findByNumberNot(Long number);

    List<Product> findByUpdatedAtNull();
    List<Product> findByUpdatedAtIsNull();
    List<Product> findByUpdatedAtNotNull();
    List<Product> findByUpdatedAtIsNotNull();

//    Product findByisActiveTrue();
//    Product findByisActiveIsTrue();
//    Product findByisActiveFalse();
//    Product findByisActiveIsFalse();

    Product findByNumberAndName(Long number, String name);
    Product findByNumberOrName(Long number, String name);

    List<Product> findByPriceIsGreaterThan(Long price);
    List<Product> findByPriceGreaterThan(Long price);
    List<Product> findByPriceGreaterThanEqual(Long price);
    List<Product> findByPriceIsLessThan(Long price);
    List<Product> findByPriceLessThan(Long price);
    List<Product> findByPriceLessThanEqual(Long price);
    List<Product> findByPriceIsBetween(Long lowPirce, Long highPrice);
    List<Product> findByPriceBetween(Long lowPrice, Long highPrice);

    List<Product> findByNameLike(String name);
    List<Product> findByNameIsLike(String name);
    List<Product> findByNameContains(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameIsContaining(String name);

    List<Product> findByNameStartsWith(String name);
    List<Product> findByNameIsStartingWith(String name);
    List<Product> findByNameStartingWith(String name);

    List<Product> findByNameEndsWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameIsEndingWith(String name);

    List<Product> findByNameOrderByNumberAsc(String name);
    List<Product> findByNameOrderByNumberDesc(String name);

    List<Product> findByNameOrderByPriceAscStockDesc(String name);

    List<Product> findByName(String name, Sort sort);

    Page<Product> findByName(String name, Pageable pageable);

    List<Product> findByNameContainingOrderByStockAsc(String name);
    List<Product> findByNameContainingOrderByStockDesc(String name);
    List<Product> findByNameContainingOrderByStockDescPriceAsc(String name);

    List<Product> findByNameContaining(String name, Sort sort);

    List<Product> findByPriceGreaterThan(Integer price, Pageable pageable);
    List<Product> findByNameIsContaining(String name, Pageable pageable);

    @Query("SELECT p FROM Product AS p WHERE p.name = ?1")   //직접 쿼리 작성, ?1은 인자(첫번째 파라미터)
    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findByNameParam(@Param("name") String name);   //파라미터 순서에 상관없이 사용하기 위해 @Param 사용

    @Query("SELECT p.name, p.price, p.stock FROM Product p WHERE p.name = :name")  //원하는 칼럼값만 추출
    List<Object[]> findByNameParam2(@Param("name") String name);

    @Query("SELECT p.name, p.price FROM Product p WHERE p.name like %:name% and p.price <= :price") //nativeQuery 속성값 true로 하면 네이티브쿼리 사용가능
    List<Object[]> findByNameContainingParam3(@Param("name") String name, @Param("price") Integer price);

}
