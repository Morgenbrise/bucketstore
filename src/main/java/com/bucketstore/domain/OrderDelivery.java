package com.bucketstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class OrderDelivery extends BaseEntity {
    @Id
    private Long orderId;
    private String receiverName;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String deliveryMessage;

    @OneToOne
    @MapsId
    private Order order;
}