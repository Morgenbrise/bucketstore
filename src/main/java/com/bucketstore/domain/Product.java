package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
}
