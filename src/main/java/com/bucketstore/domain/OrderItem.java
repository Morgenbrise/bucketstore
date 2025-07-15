package com.bucketstore.domain;

import com.bucketstore.enums.status.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Integer quantity;
    private Integer itemPrice;
    @Convert(converter = OrderItemStatus.ConverterImpl.class)
    @Builder.Default
    private OrderItemStatus itemStatus = OrderItemStatus.ORDERED;
    private Integer deliveryFee;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @Setter
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "optionId")
    private ProductOption option;

    public void cancel() {
        this.itemStatus = OrderItemStatus.CANCELED;
        this.deliveryFee = 3000;
    }

    public boolean isCanceled() {
        return this.itemStatus == OrderItemStatus.CANCELED;
    }
}
