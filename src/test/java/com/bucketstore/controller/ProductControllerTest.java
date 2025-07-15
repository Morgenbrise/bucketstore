package com.bucketstore.controller;

import com.bucketstore.dto.product.ProductDTO;
import com.bucketstore.enums.ProductDisplayableCode;
import com.bucketstore.enums.SortDirection;
import com.bucketstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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
                        .productCode("CODE002")
                        .productName("상품B")
                        .basePrice(20000)
                        .build(),
                ProductDTO.builder()
                        .productCode("CODE001")
                        .productName("상품A")
                        .basePrice(10000)
                        .build()
        );

        when(productService.findProducts(any())).thenReturn(mockProducts);

        // when & then
        mockMvc.perform(get("/api/products/search")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort[0].code", "PRICE")
                        .param("sort[0].direction", "DESC")
                        .param("sort[1].code", "NAME")
                        .param("sort[1].direction", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].PRODUCT_CODE").value("CODE002"))
                .andExpect(jsonPath("$[1].PRODUCT_CODE").value("CODE001"))
                .andDo(MockMvcResultHandlers.print());

    }
}