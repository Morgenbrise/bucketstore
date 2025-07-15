package com.bucketstore.service;

import com.bucketstore.common.dto.SortConditionDTO;
import com.bucketstore.domain.Product;
import com.bucketstore.dto.product.ProductDTO;
import com.bucketstore.dto.product.ProductSearchRequest;
import com.bucketstore.enums.SortDirection;
import com.bucketstore.repository.product.ProductRepository;
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
    private ProductService productService;

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
    void 상품명으로_오름차순_정렬() {
        ProductSearchRequest request = new ProductSearchRequest(0, 2, List.of(new SortConditionDTO("NAME", SortDirection.ASC)));

        List<ProductDTO> result = productService.findProducts(request);

        assertEquals(2, result.size());
        assertEquals("상품A", result.get(0).getProductName());
        assertEquals("상품B", result.get(1).getProductName());
    }

    @Test
    void 가격으로_내림차순_정렬() {
        ProductSearchRequest request = new ProductSearchRequest(0, 1, List.of(new SortConditionDTO("PRICE", SortDirection.DESC)));
        List<ProductDTO> result = productService.findProducts(request);

        assertEquals(1, result.size());
        assertEquals(30000, result.get(0).getBasePrice());
    }

    @Test
    void 날짜을_내림차순_가격을_오름차순_정렬() {
        ProductSearchRequest request = new ProductSearchRequest(
                0, 1,
                List.of(new SortConditionDTO("CREATED", SortDirection.DESC),
                        new SortConditionDTO("PRICE", SortDirection.ASC))
        );
        List<ProductDTO> result = productService.findProducts(request);

        assertEquals(1, result.size());
        assertEquals(10000, result.get(0).getBasePrice());
    }

    @Test
    void 정렬조건_없을때는_기본값으로_정렬() {
        ProductSearchRequest request = new ProductSearchRequest(0, 2, List.of());
        List<ProductDTO> result = productService.findProducts(request);

        assertEquals(2, result.size());
        // 기본값이 CREATED DESC면, 생성 순서 반대로 나와야 함
    }
}
