package com.bucketstore.service;

import com.bucketstore.domain.OrderItem;
import com.bucketstore.domain.Orders;
import com.bucketstore.domain.Product;
import com.bucketstore.repository.order.OrdersRepository;
import com.bucketstore.repository.orderItem.OrderItemRepository;
import com.bucketstore.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
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

@DisplayName("OrderService - 주문 생성 테스트")
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@SpringBootTest
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
        List<String> productCodes = List.of("PROD001", "PROD002");

        // when
        ordersService.createOrder(productCodes);

        // then
        List<Orders> orders = ordersRepository.findAll();
        assertEquals(1, orders.size());

        List<OrderItem> items = orderItemRepository.findAll();
        assertEquals(2, items.size());

        assertTrue(items.stream().anyMatch(i -> i.getProduct().getProductCode().equals("PROD001")));
        assertTrue(items.stream().anyMatch(i -> i.getProduct().getProductCode().equals("PROD002")));
    }

}
