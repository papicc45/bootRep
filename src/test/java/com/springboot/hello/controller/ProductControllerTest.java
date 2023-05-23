package com.springboot.hello.controller;

import com.google.gson.Gson;
import com.springboot.hello.dto.ProductDTO;
import com.springboot.hello.dto.ProductResponseDTO;
import com.springboot.hello.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)  //대상 클래스만 로드해 테스트 실행
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean   //가짜객체 생성 후 주입
    ProductServiceImpl productService;

    @Test
    @DisplayName("MockMvc를 통한 Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception {

        given(productService.getProduct(123L)).willReturn(new ProductResponseDTO(123L, "pen", 5000, 2000));

        String productId = "123";

        mockMvc.perform(get("/product?number=" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print());

        verify(productService).getProduct(123L);
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {

        given(productService.saveProduct(new ProductDTO("pen", 5000, 2000))).willReturn(new ProductResponseDTO(12315L, "pen", 5000, 2000));

        ProductDTO productDTO = ProductDTO.builder()
                .name("pen").price(5000).stock(2000).build();

        Gson gson = new Gson();
        String content = gson.toJson(productDTO);
        /**
         **  이 위치에서 No value a JSON path 에러 발생
         *          * @WebMvcTest가 repository 레벨까지 안거치기 때문에 실제 데이터베이스에 저장되는 값이 없어서
         *          * json-path가 계속 null인 response 반환
         */
        mockMvc.perform(post("/product").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.stock").exists())
                .andDo(print());

        verify(productService).saveProduct(new ProductDTO("pen", 500, 2000));
    }
}
