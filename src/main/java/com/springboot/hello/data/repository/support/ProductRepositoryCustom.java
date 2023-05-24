package com.springboot.hello.data.repository.support;

import com.springboot.hello.data.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findByName(String name);
}
