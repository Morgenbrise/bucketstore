package com.bucketstore.controller;

import com.bucketstore.domain.Orders;
import com.bucketstore.dto.detail.OrderDetailResponse;
import com.bucketstore.dto.order.CancelOrderRequest;
import com.bucketstore.dto.order.OrderCreateRequest;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.dto.order.OrderSearchRequest;
import com.bucketstore.service.OrderDetailService;
import com.bucketstore.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrdersService ordersService;
    private final OrderDetailService orderDetailService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "주문 생성", description = "상품 코드, 수량, 사이즈, 배송지를 입력받아 주문을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "주문이 성공적으로 생성됨")
    @ApiResponse(responseCode = "400", description = "입력값이 잘못된 경우")
    public ResponseEntity<OrderResponse> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "주문 생성 요청 DTO",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OrderCreateRequest.class))
            )
            @RequestBody @Valid OrderCreateRequest request
    ) {
        Orders orders = ordersService.createOrder(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.from(orders));
    }

    @Operation(summary = "주문 항목 취소", description = "주문 ID와 주문 항목 ID 목록을 받아 해당 항목을 취소하고, 전체 취소 시 주문 상태를 변경합니다.")
    @DeleteMapping("/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrderItems(
            @RequestBody @Valid CancelOrderRequest request
    ) {
        ordersService.cancelOrderItems(request.orderId(), request.orderItemIds());
    }

    @Operation(summary = "정렬 조건에 따라 주문 목록 조회")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @ModelAttribute OrderSearchRequest request
    ) {
        List<OrderResponse> result = ordersService.findOrders(request);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "주문 상세 조회", description = "주문 식별자로 주문 상세 정보를 조회합니다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long orderId) {
        OrderDetailResponse response = orderDetailService.findOrderDetail(orderId);
        return ResponseEntity.ok(response);
    }
}
