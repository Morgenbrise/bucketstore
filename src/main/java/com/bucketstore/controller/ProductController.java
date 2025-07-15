package com.bucketstore.controller;

import com.bucketstore.dto.ProductDTO;
import com.bucketstore.dto.ProductSearchRequest;
import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortCondition;
import com.bucketstore.enumType.SortDirection;
import com.bucketstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "상품 정렬 조회",
            description = "사용자가 입력한 정렬 기준에 따라 상품 목록을 정렬하여 조회합니다. 정렬 필드는 Enum으로 제공되며, 다중 정렬도 지원합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 응답", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )),
            @ApiResponse(responseCode = "400", description = "잘못된 정렬 필드 또는 방향"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProducts(@ModelAttribute ProductSearchRequest request) {

        List<SortCondition> conditions = request.getSort() == null ? List.of() :
                request.getSort().stream()
                        .map(dto -> new SortCondition(
                                ProductSortField.from(dto.code().getCode()),
                                SortDirection.from(dto.direction().name())))
                        .toList();

        List<ProductDTO> result = productService.findProducts(request.getPage(), request.getSize(), conditions);
        return ResponseEntity.ok(result);
    }
}
