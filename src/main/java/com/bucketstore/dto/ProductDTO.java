package com.bucketstore.dto;

import com.bucketstore.domain.Product;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDTO {

    private String productCode;
    private String productName;
    private String category;
    private String brand;
    private int basePrice;
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