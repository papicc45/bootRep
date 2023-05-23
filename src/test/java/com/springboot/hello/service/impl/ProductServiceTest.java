package com.springboot.hello.service.impl;

import com.springboot.hello.data.repository.ProductRepository;
import com.springboot.hello.dto.ProductResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class ProductServiceTest {

    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private ProductServiceImpl productService;


}
