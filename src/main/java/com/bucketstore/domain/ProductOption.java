package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProductOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    private String color;
    private String size;
    private Integer additionalPrice;
    private Integer stockQty;
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}