package com.bucketstore.service;

import com.bucketstore.common.dto.SortRequestCondition;
import com.bucketstore.domain.OrderDelivery;
import com.bucketstore.domain.OrderItem;
import com.bucketstore.domain.Orders;
import com.bucketstore.dto.order.OrderCreateRequest;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.dto.order.OrderSearchRequest;
import com.bucketstore.enums.status.OrderItemStatus;
import com.bucketstore.enums.status.OrderStatus;
import com.bucketstore.common.enums.SortDirection;
import com.bucketstore.repository.order.OrdersRepository;
import com.bucketstore.repository.orderItem.OrderItemRepository;
import com.bucketstore.repository.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderService - 주문 테스트")
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@SpringBootTest
@ActiveProfiles("test")
public class OrdersServiceTest {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void 상품_코드_리스트로_주문_생성_성공() {
        // given
        OrderCreateRequest request = new OrderCreateRequest(
                1L,
                List.of(new OrderCreateRequest.OrderItemRequest("PROD001", "M", 2)),
                new OrderCreateRequest.DeliveryRequest(
                        "홍길동",
                        "010-1234-5678",
                        "서울시 강남구",
                        "06236",
                        "문 앞에 두세요"
                )
        );

        // when
        Orders order = ordersService.createOrder(request);

        // then
        assertNotNull(order.getOrderId());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals(1, order.getOrderItems().size());

        OrderItem item = order.getOrderItems().get(0);
        assertEquals(2, item.getQuantity());
        assertEquals(1, item.getProduct().getProductId());
        assertEquals("M", item.getOption().getSize());

        OrderDelivery delivery = order.getDelivery();
        assertEquals("홍길동", delivery.getReceiverName());
        assertEquals("서울시 강남구", delivery.getAddress());
    }

    @Test
    void 특정_주문_상품을_취소하고_배송비를_차감한다() {
        Long orderId = 1L;
        List<Long> cancelIds = List.of(1L);

        // when
        ordersService.cancelOrderItems(orderId, cancelIds);

        // then
        Orders order = ordersRepository.findById(orderId).orElseThrow();
        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());

        order.getOrderItems().forEach(item ->
                assertEquals(OrderItemStatus.CANCELED, item.getItemStatus()));
    }

    @Test
    @DisplayName("주문을 총 결제금액 기준으로 내림차순 정렬하여 조회")
    void findOrdersSortedByPriceDesc() {
        // given
        OrderSearchRequest request = new OrderSearchRequest(
                0,
                10,
                List.of(new SortRequestCondition("PRICE", SortDirection.DESC))
        );

        // when
        List<OrderResponse> results = ordersService.findOrders(request);

        // then
        assertEquals(2, results.size());
        assertEquals("ORDER002", results.get(0).getOrderCode());
        assertEquals("ORDER001", results.get(1).getOrderCode());
    }

    @Test
    @DisplayName("주문을 주문일 기준 오름차순으로 정렬하여 조회")
    void findOrdersSortedByCreatedAsc() {
        OrderSearchRequest request = new OrderSearchRequest(
                0,
                10,
                List.of(new SortRequestCondition("CREATED", SortDirection.ASC))
        );

        List<OrderResponse> results = ordersService.findOrders(request);

        assertEquals("ORDER001", results.get(0).getOrderCode());
        assertEquals("ORDER002", results.get(1).getOrderCode());
    }
}
