package com.bucketstore.domain;

import com.bucketstore.enums.OrderItemStatus;
import com.bucketstore.enums.OrderStatus;
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

    @Convert(converter = OrderItemStatus.ConverterImpl.class)
    @Builder.Default
    private OrderItemStatus itemStatus = OrderItemStatus.ORDERED;

    public void cancel() {
        this.itemStatus = OrderItemStatus.CANCELED;
    }

    public boolean isCanceled() {
        return this.itemStatus == OrderItemStatus.CANCELED;
    }
}
