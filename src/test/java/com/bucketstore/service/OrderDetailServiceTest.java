package com.bucketstore.service;

import com.bucketstore.dto.detail.OrderDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("주문 상세 조회 테스트")
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
@SpringBootTest
@ActiveProfiles("test")
public class OrderDetailServiceTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    void 주문_ID로_주문_상세_조회() {
        // given
        Long orderId = 1L;

        // when
        OrderDetailResponse response = orderDetailService.findOrderDetail(orderId);

        // then
        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertNotNull(response.getItems());
        assertTrue(response.getTotalPrice() > 0);
        assertNotNull(response.getCanceledItems());
        assertTrue(response.getCanceledItems().stream().allMatch(i -> i.getStatus().equals("CANCELED")));
    }

}
