package com.bucketstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class ProductDelivery extends BaseEntity {
    @Id
    private Long productId;
    private Integer deliveryFee;
    private String deliveryMethod;
    private Integer freeDeliveryThreshold;

    @OneToOne
    @MapsId
    private Product product;
}
