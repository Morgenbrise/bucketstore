package com.bucketstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    @Setter
    private Orders order;
}