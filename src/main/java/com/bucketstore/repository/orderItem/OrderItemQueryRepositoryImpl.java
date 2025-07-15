package com.bucketstore.repository.orderItem;

import com.bucketstore.domain.QOrderItem;
import com.bucketstore.domain.QProduct;
import com.bucketstore.domain.QProductOption;
import com.bucketstore.dto.detail.OrderItemRaw;
import com.bucketstore.dto.detail.OrderItemResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemQueryRepositoryImpl implements OrderItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderItemQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderItemResponse> findByOrderId(Long orderId) {
        QOrderItem oi = QOrderItem.orderItem;
        QProduct p = QProduct.product;
        QProductOption po = QProductOption.productOption;

        List<OrderItemRaw> rawItems = queryFactory
                .select(Projections.constructor(OrderItemRaw.class,
                        p.productName.as("productName"),
                        po.size.as("size"),
                        oi.quantity,
                        oi.itemPrice,
                        oi.deliveryFee,
                        oi.itemStatus.stringValue().as("status")
                ))
                .from(oi)
                .join(oi.product, p)
                .join(oi.option, po)
                .where(oi.order.orderId.eq(orderId))
                .fetch();

        List<OrderItemResponse> itemResponses = rawItems.stream()
                .map(r -> OrderItemResponse.builder()
                        .productName(r.getProductName())
                        .size(r.getSize())
                        .quantity(r.getQuantity())
                        .itemPrice(r.getItemPrice())
                        .unitPrice(r.getItemPrice() / r.getQuantity())
                        .deliveryFee(r.getDeliveryFee())
                        .totalPrice(r.getItemPrice() - r.getDeliveryFee())
                        .status(r.getStatus())
                        .build())
                .toList();

        return itemResponses;
    }
}

