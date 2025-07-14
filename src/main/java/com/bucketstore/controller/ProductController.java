package com.bucketstore.controller;

import com.bucketstore.dto.ProductDTO;
import com.bucketstore.dto.ProductSearchRequest;
import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortCondition;
import com.bucketstore.enumType.SortDirection;
import com.bucketstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "상품", description = "상품 조회 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "여러 정렬 조건을 지정해 상품 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(@ModelAttribute ProductSearchRequest request) {

        List<SortCondition> conditions = request.getSort() == null ? List.of() :
                request.getSort().stream()
                        .map(dto -> new SortCondition(
                                ProductSortField.from(dto.code()),
                                SortDirection.from(dto.direction())))
                        .toList();

        List<ProductDTO> result = productService.findProducts(request.getPage(), request.getSize(), conditions);
        return ResponseEntity.ok(result);
    }
}
