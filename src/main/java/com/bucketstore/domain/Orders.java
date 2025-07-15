package com.bucketstore.domain;

import com.bucketstore.enums.status.OrderStatus;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderDelivery delivery;

    public void updatePrice(int totalPrice, int deliveryFee) {
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
    }

    public void addOrderItem(OrderItem item) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        this.orderItems.add(item);
        item.setOrder(this); // 양방향 연결
    }

    public void setDelivery(OrderDelivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); // 양방향 연관 유지
    }

    public void cancelOrderItem(OrderItem item) {
        item.cancel();
        this.totalPrice -= item.getItemPrice();
    }

    public void changeStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }
}
