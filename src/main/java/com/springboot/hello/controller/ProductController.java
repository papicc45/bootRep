package com.springboot.hello.controller;

import com.springboot.hello.dto.ChangeProductNameDTO;
import com.springboot.hello.dto.ProductDTO;
import com.springboot.hello.dto.ProductResponseDTO;
import com.springboot.hello.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ProductResponseDTO> getProduct(Long number) {
        ProductResponseDTO productResponseDTO = productService.getProduct(number);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH_TOKEN",
                                                                    value = "로그인 성공 후 발급받은 access_token",
                                                                    required = true, dataType = "String", paramType = "header")})
    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        long currentTime = System.currentTimeMillis();
        ProductResponseDTO productResponseDTO = productService.saveProduct(productDTO);
        LOGGER.info("[createProduct] Response Time : {}ms", System.currentTimeMillis() - currentTime);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<ProductResponseDTO> changeProductName(@RequestBody ChangeProductNameDTO changeProductNameDTO) throws Exception {
        ProductResponseDTO productResponseDTO = productService.changeProductName(changeProductNameDTO.getNumber(), changeProductNameDTO.getName());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteProduct(Long number) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }

}
