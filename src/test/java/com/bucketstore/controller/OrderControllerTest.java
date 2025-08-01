package com.bucketstore.controller;

import com.bucketstore.domain.Orders;
import com.bucketstore.dto.detail.OrderDetailResponse;
import com.bucketstore.dto.detail.OrderItemResponse;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.enums.status.OrderStatus;
import com.bucketstore.service.OrderDetailService;
import com.bucketstore.service.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersService ordersService;

    @MockBean
    private OrderDetailService orderDetailService;


    @Test
    void 주문_생성_API_성공() throws Exception {
        // given
        Orders mockOrders = Orders.builder()
                .orderCode("ORDER_20250715_ABC123")
                .orderStatus(OrderStatus.PENDING)
                .totalPrice(20000)
                .deliveryFee(3000)
                .orderDate(LocalDateTime.now())
                .build();

        given(ordersService.createOrder(any())).willReturn(mockOrders);

        String requestJson = """
            {
              "USER_ID": 1,
              "ITEMS": [
                {
                  "PRODUCT_CODE": "PROD001",
                  "SIZE": "M",
                  "QUANTITY": 2
                }
              ],
              "DELIVERY": {
                "RECEIVER_NAME": "홍길동",
                "PHONE_NUMBER": "010-1234-5678",
                "ADDRESS": "서울",
                "ZIP_CODE": "12345",
                "DELIVERY_MESSAGE": "문 앞"
              }
            }
        """;

        // when & then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ORDER_CODE").value("ORDER_20250715_ABC123"))
                .andExpect(jsonPath("$.ORDER_STATUS").value("PENDING"))
                .andExpect(jsonPath("$.TOTAL_PRICE").value(20000))
                .andExpect(jsonPath("$.DELIVERY_FEE").value(3000));
    }

    @Test
    void DELETE_메서드로_주문_항목_여러_개를_취소할_수_있다() throws Exception {
        String requestBody = """
        {
          "ORDER_ID": 1,
          "ORDER_ITEM_IDS": [1, 2]
        }
    """;

        mockMvc.perform(delete("/api/orders/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());

        verify(ordersService).cancelOrderItems(eq(1L), eq(List.of(1L, 2L)));
    }

    @Test
    @DisplayName("정렬 조건으로 주문 목록 조회")
    void getOrders_success() throws Exception {
        // given
        List<OrderResponse> orders = List.of(
                OrderResponse.builder()
                        .orderCode("ORDER001")
                        .totalPrice(30000)
                        .deliveryFee(3000)
                        .orderStatus("PENDING")
                        .build(),
                OrderResponse.builder()
                        .orderCode("ORDER002")
                        .totalPrice(10000)
                        .deliveryFee(2000)
                        .orderStatus("CANCELED")
                        .build()
        );

        given(ordersService.findOrders(any())).willReturn(orders);

        // when & then
        mockMvc.perform(get("/api/orders")
                        .param("offset", "0")
                        .param("size", "10")
                        .param("sort[0].code", "CREATED")
                        .param("sort[0].direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ORDER_CODE").value("ORDER001"))
                .andExpect(jsonPath("$[0].TOTAL_PRICE").value(30000))
                .andExpect(jsonPath("$[1].ORDER_CODE").value("ORDER002"))
                .andExpect(jsonPath("$[1].ORDER_STATUS").value("CANCELED"));

    }

    @Test
    void 특정_주문_정보를_조회_할수있다() throws Exception {
        // given
        Long orderId = 1L;
        List<OrderItemResponse> items = List.of(
                OrderItemResponse.builder()
                        .productName("상품A")
                        .size("M")
                        .quantity(1)
                        .unitPrice(10000)
                        .totalPrice(10000)
                        .itemPrice(10000)
                        .status("주문됨")
                        .build()
        );

        OrderDetailResponse response = OrderDetailResponse.builder()
                .orderId(orderId)
                .orderCode("ORDER001")
                .totalPrice(10000)
                .items(items)
                .canceledItems(Collections.emptyList())
                .build();

        given(orderDetailService.findOrderDetail(orderId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/orders/{orderId}", orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ORDER_CODE").value("ORDER001"))
                .andExpect(jsonPath("$.ITEMS[0].PRODUCT_NAME").value("상품A"))
                .andDo(MockMvcResultHandlers.print());
    }
}
