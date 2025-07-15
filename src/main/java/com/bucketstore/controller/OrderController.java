package com.bucketstore.controller;

import com.bucketstore.domain.Orders;
import com.bucketstore.dto.order.OrderCreateRequest;
import com.bucketstore.dto.order.OrderResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrdersService ordersService;

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
}
