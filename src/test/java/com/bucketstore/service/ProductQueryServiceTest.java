package com.bucketstore.service;

import com.bucketstore.domain.Product;
import com.bucketstore.dto.ProductDTO;
import com.bucketstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DisplayName("상품 조회 API 테스트")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductQueryServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productQueryService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productRepository.saveAll(List.of(
                new Product("CODE001", "상품A", "의류", "브랜드X", 10000, "설명A"),
                new Product("CODE002", "상품B", "의류", "브랜드Y", 20000, "설명B"),
                new Product("CODE003", "상품C", "의류", "브랜드Z", 30000, "설명C")
        ));
    }

    @Test
    void 상품명을_기준으로_오름차순_정렬하여_2개를_조회한다() {
        List<ProductDTO> result = productQueryService.findProducts("productName", "asc", 2);

        assertEquals(2, result.size());
        assertEquals("상품A", result.get(0).getProductName());
        assertEquals("상품B", result.get(1).getProductName());
    }

    @Test
    void 가격을_기준으로_내림차순_정렬하여_1개_조회() {
        List<ProductDTO> result = productQueryService.findProducts("basePrice", "desc", 1);
        assertEquals(1, result.size());
        assertEquals(50000, result.get(0).getBasePrice());
    }
}
