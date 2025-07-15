package com.bucketstore.service;

import com.bucketstore.domain.*;
import com.bucketstore.dto.order.OrderCreateRequest;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.dto.order.OrderSearchRequest;
import com.bucketstore.enums.status.OrderStatus;
import com.bucketstore.repository.order.OrderDeliveryRepository;
import com.bucketstore.repository.order.OrdersRepository;
import com.bucketstore.repository.orderItem.OrderItemRepository;
import com.bucketstore.repository.product.ProductRepository;
import com.bucketstore.repository.product.ProductOptionRepository;
import com.bucketstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderDeliveryRepository orderDeliveryRepository;

    private static final int CANCEL_DELIVERY_DISCOUNT = 3000;

    @Transactional
    public Orders createOrder(OrderCreateRequest request) {
        // 1. 사용자 확인
        Users user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        // 2. 주문 코드 생성
        String orderCode = generateOrderCode();

        Orders order = Orders.builder()
                .users(user)
                .orderCode(orderCode)
                .orderStatus(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        int totalPrice = 0;
        int totalDeliveryFee = 0;

        // 3. 상품 처리
        for (OrderCreateRequest.OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findByProductCode(itemRequest.productCode())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다: " + itemRequest.productCode()));

            ProductOption option = productOptionRepository.findByProductAndSize(product, itemRequest.size())
                    .orElseThrow(() -> new IllegalArgumentException("해당 사이즈의 옵션이 없습니다: " + itemRequest.productCode() + " - " + itemRequest.size()));

            if (option.getStockQty() < itemRequest.quantity()) {
                throw new IllegalArgumentException("재고가 부족합니다: " + itemRequest.productCode());
            }

            option.reduceStock(itemRequest.quantity());

            int itemPrice = (product.getBasePrice() + option.getAdditionalPrice()) * itemRequest.quantity();
            totalPrice += itemPrice;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .option(option)
                    .quantity(itemRequest.quantity())
                    .itemPrice(itemPrice)
                    .build();

            order.addOrderItem(orderItem);
        }

        // 4. 배송비 계산 (상품 개별 배송정책이 있다면 확장 가능)
        if (totalPrice < 50000) {
            totalDeliveryFee = 3000;
        }

        order.updatePrice(totalPrice, totalDeliveryFee);

        // 5. 배송지 저장
        OrderCreateRequest.DeliveryRequest d = request.delivery();

        OrderDelivery delivery = OrderDelivery.builder()
                .order(order)
                .receiverName(d.receiverName())
                .phoneNumber(d.phoneNumber())
                .address(d.address())
                .zipCode(d.zipCode())
                .deliveryMessage(d.deliveryMessage())
                .build();

        order.setDelivery(delivery);

        return ordersRepository.save(order);
    }

    private String generateOrderCode() {
        return "ORDER_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public void cancelOrderItems(Long orderId, List<Long> orderItemIds) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        for (Long itemId : orderItemIds) {
            OrderItem item = orderItemRepository.findById(itemId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 항목입니다."));

            if(!orderId.equals(item.getOrder().getOrderId())) {
                throw new IllegalStateException("주문과 주문 항목이 일치하지 않습니다.");
            }

            if (!item.isCanceled()) {
                item.cancel();
                order.cancelOrderItem(item, CANCEL_DELIVERY_DISCOUNT);  // 배송비 3,000 차감
            }
        }

        // 모든 항목이 취소됐는지 검사 후 주문 상태 변경
        boolean allCanceled = order.getOrderItems().stream().allMatch(OrderItem::isCanceled);

        if (allCanceled) {
            order.changeStatus(OrderStatus.CANCELLED);
        }
    }

    public List<OrderResponse> findOrders(OrderSearchRequest request) {
        return ordersRepository.findOrders(request);

    }
}
