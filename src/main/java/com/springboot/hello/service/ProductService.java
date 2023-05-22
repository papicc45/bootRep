package com.springboot.hello.service;

import com.springboot.hello.dto.ProductDTO;
import com.springboot.hello.dto.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO getProduct(Long number);
    ProductResponseDTO saveProduct(ProductDTO productDTO);

    ProductResponseDTO changeProductName(Long number, String name) throws Exception;

    void deleteProduct(Long number) throws Exception;
}
