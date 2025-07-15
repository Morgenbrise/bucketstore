package com.bucketstore.domain;

import com.bucketstore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderCode;

    @Convert(converter = OrderStatus.ConverterImpl.class)
    private OrderStatus orderStatus;
    private Integer totalPrice;
    private Integer deliveryFee;
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private OrderDelivery delivery;
}
