package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productCode;
    private String productName;
    private String category;
    private String brand;
    private Integer basePrice;
    private String description;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> options = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private ProductDelivery delivery;

    @Builder
    public Product(String productCode, String productName, String category, String brand, int basePrice, String description) {
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.brand = brand;
        this.basePrice = basePrice;
        this.description = description;
    }

}
