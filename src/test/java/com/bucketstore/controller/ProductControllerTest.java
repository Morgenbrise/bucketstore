package com.bucketstore.controller;

import com.bucketstore.dto.ProductDTO;
import com.bucketstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void 상품정렬_요청이_정상적으로_처리된다() throws Exception {
        // given
        List<ProductDTO> mockProducts = List.of(
                ProductDTO.builder()
                        .productCode("CODE003")
                        .productName("상품C")
                        .basePrice(30000)
                        .build(),
                ProductDTO.builder()
                        .productCode("CODE002")
                        .productName("상품B")
                        .basePrice(20000)
                        .build()
        );

        when(productService.findProducts("basePrice", "desc", 2))
                .thenReturn(mockProducts);

        // when & then
        mockMvc.perform(get("/api/products")
                        .param("sortBy", "basePrice")
                        .param("direction", "desc")
                        .param("limit", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].PRODUCT_CODE").value("CODE003"))
                .andExpect(jsonPath("$[1].PRODUCT_CODE").value("CODE002"));
    }
}