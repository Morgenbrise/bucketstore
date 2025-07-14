package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Integer quantity;
    private Integer itemPrice;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "optionId")
    private ProductOption option;
}
