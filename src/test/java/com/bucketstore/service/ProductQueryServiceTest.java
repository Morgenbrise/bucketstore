package com.bucketstore.service;

import com.bucketstore.common.utils.PageRequestUtils;
import com.bucketstore.domain.Product;
import com.bucketstore.dto.ProductDTO;
import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortCondition;
import com.bucketstore.enumType.SortDirection;
import com.bucketstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
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
    void 상품명으로_오름차순_정렬() {

        List<SortCondition> sortConditions = List.of(new SortCondition(ProductSortField.NAME, SortDirection.ASC));
        List<ProductDTO> result = productQueryService.findProducts(0, 2, sortConditions);

        assertEquals(2, result.size());
        assertEquals("상품A", result.get(0).getProductName());
        assertEquals("상품B", result.get(1).getProductName());
    }

    @Test
    void 가격으로_내림차순_정렬() {
        List<SortCondition> sortConditions = List.of(new SortCondition(ProductSortField.PRICE, SortDirection.DESC));

        List<ProductDTO> result = productQueryService.findProducts(0, 1, sortConditions);

        assertEquals(1, result.size());
        assertEquals(30000, result.get(0).getBasePrice());
    }

    @Test
    void 정렬조건_없을때는_기본값으로_정렬() {
        Pageable pageable = PageRequestUtils.of(0, 2, List.of());

        List<ProductDTO> result = productQueryService.findProducts(0, 2, List.of());

        assertEquals(2, result.size());
        // 기본값이 CREATED DESC면, 생성 순서 반대로 나와야 함
    }
}
