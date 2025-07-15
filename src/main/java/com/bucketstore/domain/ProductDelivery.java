package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductDelivery extends BaseEntity {
    @Id
    private Long productId;
    private Integer deliveryFee;
    private String deliveryMethod;
    private Integer freeDeliveryThreshold;

    @OneToOne
    @MapsId
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
