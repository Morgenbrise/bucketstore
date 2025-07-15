package com.bucketstore.dto;

import com.bucketstore.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "상품 응답 DTO")
public class ProductDTO {

    @Schema(description = "상품 코드", example = "PROD001")
    private String productCode;

    @Schema(description = "상품명", example = "시그니처 반팔티")
    private String productName;

    @Schema(description = "카테고리", example = "의류")
    private String category;

    @Schema(description = "브랜드명", example = "무신사 스탠다드")
    private String brand;

    @Schema(description = "기본 가격 (원 단위)", example = "19900")
    private int basePrice;

    @Schema(description = "상품 설명", example = "편안하고 깔끔한 디자인의 반팔 티셔츠")
    private String description;

    public static ProductDTO from(Product product) {
        return ProductDTO.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .category(product.getCategory())
                .brand(product.getBrand())
                .basePrice(product.getBasePrice())
                .description(product.getDescription())
                .build();
    }
}