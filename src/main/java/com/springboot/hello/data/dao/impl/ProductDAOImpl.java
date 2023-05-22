package com.springboot.hello.data.dao.impl;

import com.springboot.hello.data.dao.ProductDAO;
import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

//클래스를 스프링이 관리하는 빈 등록하려면 component나 service 어노테이션 사용
@Component
public class ProductDAOImpl implements ProductDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductDAO.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product insertProduct(Product product) {
        LOGGER.info("[saveProduct] product : {}", product.toString());
        Product savedProduct = productRepository.save(product);

        LOGGER.info("[saveProduct] product : {}", savedProduct.toString());
        return savedProduct;
    }

    @Override
    public Product selectProduct(Long number) {
        LOGGER.info("[getProduct] input number : {}", number);
        Product selectedProduct = productRepository.getById(number);

        LOGGER.info("[getProduct] product number : {}, name : {}", selectedProduct.getNumber(), selectedProduct.getName());
        return selectedProduct;
    }

    //JPA에서는 update라는 키워드 사용하지 않고, db에서 값을 가져온 후 save로 다시 저장(더티 체크라는 변경감지 수행)
    @Override
    public Product updateProductName(Long number, String name) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        Product updatedProduct;
        if(selectedProduct.isPresent()) {
            Product product = selectedProduct.get();

            product.setName(name);
            product.setUpdatedAt(LocalDateTime.now());

            updatedProduct = productRepository.save(product);
        } else {
            throw new Exception();
        }

        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        if(selectedProduct.isPresent()) {
            Product product = selectedProduct.get();

            productRepository.delete(product);
        } else {
            throw new Exception();
        }
    }
}
